
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

    private static final SocketAddress DNS_SERVER = new InetSocketAddress("127.0.1.1", 53);



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

            DatagramChannel serverChannel = DatagramChannel.open();
            serverChannel.bind(new InetSocketAddress(SERVER_PORT));
            serverChannel.configureBlocking(false);

            SelectionKey serverChannelKey = serverChannel.register(selector, SelectionKey.OP_READ);

            System.out.println("Listening on port " + SERVER_PORT);

            ByteBuffer buffer = ByteBuffer.allocate(MAX_PACKET_SIZE);

            HashMap<SelectionKey,SocketAddress> recursiveRequests = new HashMap<>();


            while (true) {

                selector.select();

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
                            System.out.println("NEW REQUEST");

                            SocketAddress sender = currentChannel.receive(buffer);

                            buffer.flip();

                            Message message = Message.parseMessage(buffer);

                            System.out.println(message.toString());

                            // A new request for a blocked url, the channel is set to write mode
                            if (tree.isBlocked(message.getQuestion(0).getUrl())) {

                                System.out.println("BLOCKED");

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
                                System.out.println("UNBLOCKED");

                                DatagramChannel datagramChannel = DatagramChannel.open();
                                datagramChannel.configureBlocking(false);

                                datagramChannel.connect(DNS_SERVER);


                                SelectionKey recursiveKey = datagramChannel.register(selector, SelectionKey.OP_READ);
                                buffer.flip();
                                datagramChannel.write(buffer);

                                recursiveRequests.put(recursiveKey, sender);

                                //recursiveKey.interestOps(SelectionKey.OP_READ);

                            }

                            key.interestOps(SelectionKey.OP_READ);



                        }
                        // 2 - A response from google's dns server was received
                        else {
                            System.out.println("RECURSIVE REQUEST RECEIVED");

                            DatagramChannel recursiveChannel = (DatagramChannel) key.channel();

                            ByteBuffer byteBuffer = ByteBuffer.allocate(MAX_PACKET_SIZE);

                            SocketAddress dest = recursiveRequests.remove(key);
                            recursiveChannel.read(byteBuffer);
                            byteBuffer.flip();


                            serverChannel.send(byteBuffer, dest);

                        }




                    } else if (key.isWritable()) { //TODO(migafgarcia): check if we can write and if the answer has been computed and send it

                        System.out.println("NO");

                        key.interestOps(SelectionKey.OP_READ);

                    }

                    selectedKeys.remove();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
