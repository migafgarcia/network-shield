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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class NetworkShield {

    private static final int SERVER_PORT = 8080;
    private static final int MAX_PACKET_SIZE = 512;

    private final SocketAddress GOOGLE_DNS_SERVER = new InetSocketAddress("8.8.8.8", 53);

    public static void main(String[] args) {

        try {

            // Read blocklist and create HostsTree object
            HostsTree tree = new HostsTree();

            try (BufferedReader br = new BufferedReader(new FileReader("blocklist.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    tree.addUrl(line);
                }
            }

            Selector selector = Selector.open();

            DatagramChannel channel = DatagramChannel.open();

            channel.bind(new InetSocketAddress(SERVER_PORT));
            channel.configureBlocking(false);

            SelectionKey datagramChannelKey = channel.register(selector, SelectionKey.OP_READ);

            System.out.println("Listening on port " + SERVER_PORT);

            ByteBuffer incomingBuffer = ByteBuffer.allocate(MAX_PACKET_SIZE);
            //ByteBuffer outgoingBuffer = ByteBuffer.allocate(MAX_PACKET_SIZE);
            SocketAddress address = null;

            LinkedHashMap<SocketAddress, HashSet<ByteBuffer>> outgoingMessageQueue;





            while (true) {

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
                        if(key.equals(datagramChannelKey)) {
                            System.out.println("NEW REQUEST");

                            SocketAddress sender = currentChannel.receive(incomingBuffer);
                            incomingBuffer.flip();
                            Message message = Message.parseMessage(incomingBuffer);

                            if(tree.isBlocked(message.getQuestion(0).getUrl())) {
                                Message response = new Message(
                                        message.getHeader().getMessageId(),
                                        true,
                                        Opcode.QUERY,
                                        true,
                                        false,
                                        false,
                                        true,
                                        ResponseCode.NAME_ERROR);
                            }
                            else {

                            }

                        }
                        else {

                        }










                        //response.toBytes(responseBuffer);
                        outgoingBuffer.flip();

                        DatagramChannel datagramChannel = DatagramChannel.open();

                        datagramChannel.configureBlocking(false);
                        datagramChannel.register(selector, SelectionKey.OP_WRITE);

                        key.interestOps(SelectionKey.OP_WRITE);

                    } else if (key.isWritable()) { //TODO(migafgarcia): check if we can write and if the answer has been computed and send it
                        //write(key);

                        DatagramChannel chan = (DatagramChannel) key.channel();
                        if(address != null)
                            chan.send(outgoingBuffer, address);

                        outgoingBuffer.clear();
                        key.interestOps(SelectionKey.OP_READ);

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
