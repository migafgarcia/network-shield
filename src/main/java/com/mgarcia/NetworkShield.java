package com.mgarcia;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mgarcia.dns.Message;
import com.mgarcia.dns.codes.Opcode;
import com.mgarcia.dns.codes.ResponseCode;
import com.mgarcia.hosts.HostsTree;
import com.mgarcia.settings.Settings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;


public class NetworkShield {

    private static final int MAX_PACKET_SIZE = 512;

    private static final Logger logger = LoggerFactory.getLogger("NetworkShield");

    public static void main(String[] args) {

        logger.info("Starting NetworkShield");

        Gson gson = new Gson();

        Settings settings;

        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("ns.conf"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            System.exit(0);
        }
        settings = gson.fromJson(reader, Settings.class);

        logger.info(gson.toJson(settings));

        SocketAddress recursiveDnsServer = new InetSocketAddress(settings.getRecursiveDnsServerHost(), settings.getRecursiveDnsServerPort());

        HostsTree blocklist = settings.generateBlocklist();
        
        logger.info(System.getProperties().toString());
        // Read blocklist and create HostsTree object


        Selector selector = null;
        DatagramChannel serverChannel = null;
        SelectionKey serverChannelKey = null;
        ByteBuffer buffer = ByteBuffer.allocate(MAX_PACKET_SIZE);
        HashMap<SelectionKey, RecursiveRequest> recursiveRequests = new HashMap<>();

        long usage;


        try {
            selector = Selector.open();

            serverChannel = DatagramChannel.open();
            serverChannel.bind(new InetSocketAddress(settings.getServerPort()));
            serverChannel.configureBlocking(false);

            serverChannelKey = serverChannel.register(selector, SelectionKey.OP_READ);

            logger.info("Listening on port " + settings.getServerPort());


        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        while (true) {

            try {
                selector.select();
                usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                logger.info("MEM: " + usage / 1048576 + " MB");


                Iterator selectedKeys = selector.selectedKeys().iterator();

                while (selectedKeys.hasNext()) {
                    SelectionKey key = (SelectionKey) selectedKeys.next();

                    DatagramChannel currentChannel = (DatagramChannel) key.channel();

                    /*
                     * 1 - A new request was received (key must equal datagramChannelKey)
                     * 2 - A response from google's com.mgarcia.dns server was received
                     */
                    if (key.isReadable()) {

                        buffer.clear();

                        // 1 - A new request was received (key must equal datagramChannelKey)


                        if (key.equals(serverChannelKey)) {


                            SocketAddress sender = currentChannel.receive(buffer);

                            buffer.flip();

                            Message message = Message.parseMessage(buffer);


                            logger.debug(message.toString());

                            String url = message.getQuestion(0).getUrl();

                            // A new request for a blocked url, the channel is set to write mode
                            if (blocklist.isBlocked(url)) {
                                logger.info("ID: " + message.getHeader().getMessageId() + " - Request for " + url + ": blocked");

                                Message blockedResponse = new Message(
                                        message.getHeader().getMessageId(),
                                        true,
                                        Opcode.QUERY,
                                        true,
                                        false,
                                        false,
                                        true,
                                        ResponseCode.NAME_ERROR);

                                ByteBuffer responseBuffer = ByteBuffer.allocate(MAX_PACKET_SIZE);
                                blockedResponse.toBytes(responseBuffer);
                                responseBuffer.flip();

                                currentChannel.send(responseBuffer, sender);

                            }
                            // A request needs to be recursively resolved
                            else {
                                logger.info("ID: " + message.getHeader().getMessageId() + " - Request for " + url + ": unblocked");

                                DatagramChannel datagramChannel = DatagramChannel.open();
                                datagramChannel.configureBlocking(false);

                                datagramChannel.connect(recursiveDnsServer);


                                SelectionKey recursiveKey = datagramChannel.register(selector, SelectionKey.OP_READ);
                                buffer.flip();
                                logger.info("ID: " + message.getHeader().getMessageId() + " - Relaying request to " + recursiveDnsServer.toString());
                                datagramChannel.write(buffer);

                                recursiveRequests.put(recursiveKey,
                                        new RecursiveRequest(message.getHeader().getMessageId(), recursiveKey, sender, System.currentTimeMillis()));

                                //recursiveKey.interestOps(SelectionKey.OP_READ);

                            }

                            key.interestOps(SelectionKey.OP_READ);


                        }
                        // 2 - A response from google's com.mgarcia.dns server was received
                        else {


                            DatagramChannel recursiveChannel = (DatagramChannel) key.channel();

                            ByteBuffer byteBuffer = ByteBuffer.allocate(MAX_PACKET_SIZE);

                            RecursiveRequest recursiveRequest = recursiveRequests.remove(key);
                            logger.info("ID: " + recursiveRequest.getMessageId() + " - Recursive request received");

                            SocketAddress dest = recursiveRequest.getSender();
                            recursiveChannel.read(byteBuffer);
                            byteBuffer.flip();


                            serverChannel.send(byteBuffer, dest);

                        }


                    } else if (key.isWritable()) { //TODO(migafgarcia): check if we can write and if the answer has been computed and send it

                        logger.warn("Socket should be in read mode");

                        key.interestOps(SelectionKey.OP_READ);

                    }


                    selectedKeys.remove();

                }

                // Passively remove recursive requests with more than TIMEOUT_MS ms
                long delta = System.currentTimeMillis() - settings.getRecursiveRequestTimeout();

                recursiveRequests.values().removeIf(recursiveRequest -> recursiveRequest.getTimestamp() < delta);

                logger.info("REC: " + recursiveRequests.values().toString());
            } catch (BufferUnderflowException | IOException e) {
                e.printStackTrace();
            }
        }

    }

}
