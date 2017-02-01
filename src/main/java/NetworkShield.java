
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dns.Message;
import dns.codes.Opcode;
import dns.codes.ResponseCode;
import hosts.HostsTree;

import java.io.BufferedReader;
import java.io.File;
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

    private static final int SERVER_PORT = 53;
    private static final int MAX_PACKET_SIZE = 512;
    private static final long TIMEOUT_MS = 10000;

    private static final SocketAddress DNS_SERVER = new InetSocketAddress("8.8.8.8", 53);

    private static final Logger logger = LoggerFactory.getLogger(NetworkShield.class);


    public static void main(String[] args) {

        logger.info("Starting NetworkShield");

        logger.info(System.getProperties().toString());
        // Read blocklist and create HostsTree object
        HostsTree blocklist = new HostsTree();

        File blocklistDirectory = new File("blocklists");

        if (!blocklistDirectory.isDirectory()) {
            logger.error("Invalid blocklist directory: " + blocklistDirectory.getAbsolutePath());
            System.exit(0);
        }

        File[] blocklistFiles = blocklistDirectory.listFiles();
        if (blocklistFiles == null) {
            logger.error("Error listing blocklists directory: " + blocklistDirectory.getAbsolutePath());
            System.exit(0);
        }

        for (File blocklistFile : blocklistFiles) {
            logger.info("Loading file: " + blocklistFile.getAbsolutePath());
            try (BufferedReader br = new BufferedReader(new FileReader(blocklistFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    blocklist.addUrl(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }

        long usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        logger.info("Loaded blocklist");

        Selector selector = null;
        DatagramChannel serverChannel = null;
        SelectionKey serverChannelKey = null;
        ByteBuffer buffer = ByteBuffer.allocate(MAX_PACKET_SIZE);
        HashMap<SelectionKey, RecursiveRequest> recursiveRequests = new HashMap<>();

        try {
            selector = Selector.open();

            serverChannel = DatagramChannel.open();
            serverChannel.bind(new InetSocketAddress(SERVER_PORT));
            serverChannel.configureBlocking(false);

            serverChannelKey = serverChannel.register(selector, SelectionKey.OP_READ);

            logger.info("Listening on port " + SERVER_PORT);


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
                     * 2 - A response from google's dns server was received
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

                                datagramChannel.connect(DNS_SERVER);


                                SelectionKey recursiveKey = datagramChannel.register(selector, SelectionKey.OP_READ);
                                buffer.flip();
                                logger.info("ID: " + message.getHeader().getMessageId() + " - Relaying request to " + DNS_SERVER.toString());
                                datagramChannel.write(buffer);

                                recursiveRequests.put(recursiveKey,
                                        new RecursiveRequest(message.getHeader().getMessageId(), recursiveKey, sender, System.currentTimeMillis()));

                                //recursiveKey.interestOps(SelectionKey.OP_READ);

                            }

                            key.interestOps(SelectionKey.OP_READ);


                        }
                        // 2 - A response from google's dns server was received
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
                long delta = System.currentTimeMillis() - TIMEOUT_MS;

                recursiveRequests.values().removeIf(recursiveRequest -> recursiveRequest.getTimestamp() < delta);

                logger.info("REC: " + recursiveRequests.values().toString());
            } catch (BufferUnderflowException | IOException e) {
                e.printStackTrace();
            }
        }

    }

}
