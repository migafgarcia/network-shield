
import dns.Message;
import dns.codes.Opcode;
import dns.codes.ResponseCode;
import hosts.HostsTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class NetworkShield {

    private static final int SERVER_PORT = 8080;
    private static final int MAX_PACKET_SIZE = 512;

    private static final SocketAddress DNS_SERVER = new InetSocketAddress("192.168.1.1", 53);

    public static void main(String[] args) {

        try {

            // Read blocklist and create HostsTree object
            HostsTree tree = new HostsTree();

            int i = 0;

            try (BufferedReader br = new BufferedReader(new FileReader("blocklists/trackers.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    tree.addUrl(line);
                    i++;
                }
            }

            try (BufferedReader br = new BufferedReader(new FileReader("blocklists/unifiedhosts_fakenews_gambling_porn_social.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    tree.addUrl(line);
                    i++;
                }
            }

            System.out.println("MAIN SIZE: " + i + " TREE SIZE: " + tree.getSize());

            Selector selector = Selector.open();

            DatagramChannel channel = DatagramChannel.open();
            channel.bind(new InetSocketAddress(SERVER_PORT));
            channel.configureBlocking(false);

            SelectionKey datagramChannelKey = channel.register(selector, SelectionKey.OP_READ);

            System.out.println("Listening on port " + SERVER_PORT);

            ByteBuffer incomingBuffer = ByteBuffer.allocate(MAX_PACKET_SIZE);

            HashMap<SelectionKey, WriteMessage> recursiveRequestsQueue = new HashMap<>();
            Queue<WriteMessage> responseQueue = new LinkedList<>();


            while (true) {

                System.out.println("recursiveRequestsQueue: " + recursiveRequestsQueue.size());
                System.out.println("responseQueue: " + responseQueue.size());

                selector.select();

                Iterator selectedKeys = selector.selectedKeys().iterator();

                while (selectedKeys.hasNext()) {
                    // This is
                    SelectionKey key = (SelectionKey) selectedKeys.next();

                    /*
                     * 1 - A new request was received (key must equal datagramChannelKey)
                     * 2 - A response from google's dns server was received
                     */
                    if (key.isReadable()) {

                        DatagramChannel currentChannel = (DatagramChannel) key.channel();

                        incomingBuffer.clear();

                        // 1 - A new request was received (key must equal datagramChannelKey)
                        if (key.equals(datagramChannelKey)) {
                            System.out.print("NEW REQUEST");

                            SocketAddress sender = currentChannel.receive(incomingBuffer);
                            incomingBuffer.flip();
                            Message message = Message.parseMessage(incomingBuffer);

                            // A new request for a blocked url, the channel is set to write mode
                            if (tree.isBlocked(message.getQuestion(0).getUrl())) {

                                System.out.println(" BLOCKED");

                                Message response = new Message(
                                        message.getHeader().getMessageId(),
                                        true,
                                        Opcode.QUERY,
                                        true,
                                        false,
                                        false,
                                        true,
                                        ResponseCode.NAME_ERROR);

                                ByteBuffer responseBuffer = ByteBuffer.allocate(MAX_PACKET_SIZE);
                                response.toBytes(responseBuffer);
                                responseBuffer.flip();
                                responseQueue.add(new WriteMessage(key, sender, responseBuffer));
                            }
                            // A request needs to be recursively resolved
                            else {
                                System.out.println(" NOT BLOCKED");

                                DatagramChannel recursiveRequestChannel = DatagramChannel.open();

                                recursiveRequestChannel.configureBlocking(false);

                                SelectionKey recursiveRequestKey = recursiveRequestChannel.register(selector, SelectionKey.OP_WRITE);

                                ByteBuffer recursiveBuffer = ByteBuffer.allocate(incomingBuffer.capacity());

                                incomingBuffer.rewind();
                                recursiveBuffer.put(incomingBuffer);
                                incomingBuffer.rewind();
                                recursiveBuffer.flip();

                                recursiveRequestsQueue.put(recursiveRequestKey, new WriteMessage(recursiveRequestKey, sender, recursiveBuffer));

                            }

                            key.interestOps(SelectionKey.OP_WRITE);

                        }
                        // 2 - A response from google's dns server was received
                        else {
                            System.out.println("RECURSIVE REQUEST RECEIVED");

                            ByteBuffer responseBuffer = ByteBuffer.allocate(MAX_PACKET_SIZE);

                            currentChannel.receive(responseBuffer);
                            responseBuffer.flip();

                            WriteMessage m = recursiveRequestsQueue.remove(key);

                            m.setBuffer(responseBuffer);

                            responseQueue.add(m);

                            key.cancel();

                            datagramChannelKey.interestOps(SelectionKey.OP_WRITE);

                        }




                    } else if (key.isWritable()) { //TODO(migafgarcia): check if we can write and if the answer has been computed and send it
                        //write(key);

                        DatagramChannel currentChannel = (DatagramChannel) key.channel();

                        if (key.equals(datagramChannelKey)) {
                            while (responseQueue.size() > 0) {
                                System.out.println("Response sent");
                                WriteMessage m = responseQueue.remove();
                                currentChannel.send(m.getBuffer(), m.getAddress());
                            }
                        } else {
                            Iterator<WriteMessage> itr = recursiveRequestsQueue.values().iterator();

                            while(itr.hasNext()) {

                                WriteMessage m = itr.next();
                                if(!m.isSent()) {
                                    currentChannel.send(m.getBuffer(), DNS_SERVER);
                                    System.out.println("Recursive request sent");
                                }

                                m.setSent(true);
                            }

                        }


                        key.interestOps(SelectionKey.OP_READ);

                    }


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
