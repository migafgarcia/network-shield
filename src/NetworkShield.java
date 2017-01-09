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
import java.util.Iterator;

public class NetworkShield {

    private static final int SERVER_PORT = 8080;
    private static final int MAX_PACKET_SIZE = 512;

    public static void main(String[] args) {

        try {
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
            channel.register(selector, SelectionKey.OP_READ);

            System.out.println("Listening on port " + SERVER_PORT);
            ByteBuffer responseBuffer = ByteBuffer.allocate(MAX_PACKET_SIZE);
            SocketAddress address = null;
            while (true) {
                selector.select();

                Iterator selectedKeys = selector.selectedKeys().iterator();

                while (selectedKeys.hasNext()) {
                    SelectionKey key = (SelectionKey) selectedKeys.next();
                    //selectedKeys.remove();

                    if (key.isReadable()) { //TODO(migafgarcia): read, parse question and store the answer

                        DatagramChannel chan = (DatagramChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(MAX_PACKET_SIZE);

                        address = chan.receive(byteBuffer);
                        byteBuffer.flip();

                        Message message = Message.parseMessage(byteBuffer);

                        System.out.println(message.getQuestion(0).getUrl());

                        Message response = new Message(
                                message.getHeader().getMessageId(),
                                true,
                                Opcode.QUERY,
                                true,
                                false,
                                false,
                                true,
                                ResponseCode.NAME_ERROR);

                        response.toBytes(responseBuffer);
                        responseBuffer.flip();

                        key.interestOps(SelectionKey.OP_WRITE);

                    } else if (key.isWritable()) { //TODO(migafgarcia): check if we can write and if the answer has been computed and send it
                        //write(key);
                        DatagramChannel chan = (DatagramChannel) key.channel();
                        if(address != null)
                            chan.send(responseBuffer, address);

                        responseBuffer.clear();
                        key.interestOps(SelectionKey.OP_READ);

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
