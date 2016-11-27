import dns.section.DnsHeader;
import dns.section.DnsQuestion;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.*;
import java.text.ParseException;
import java.util.Iterator;

public class DnsServer {

    private static final int SERVER_PORT = 8080;
    private static final int MAX_PACKET_SIZE = 512;

    public static void main(String[] args) {

        try {

            Selector selector = Selector.open();

            DatagramChannel channel = DatagramChannel.open();

            channel.bind(new InetSocketAddress(SERVER_PORT));
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);

            System.out.println("Listening on port " + SERVER_PORT);

            while (true) {
                selector.select();

                Iterator selectedKeys = selector.selectedKeys().iterator();

                while (selectedKeys.hasNext()) {
                    SelectionKey key = (SelectionKey) selectedKeys.next();
                    selectedKeys.remove();

                    if (key.isReadable()) { //TODO(migafgarcia): read, parse question and store the answer

                        DatagramChannel chan = (DatagramChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(MAX_PACKET_SIZE);

                        chan.receive(byteBuffer);
                        byteBuffer.flip();
                        System.out.println(DnsHeader.parseHeader(byteBuffer).toString());

                        System.out.println(DnsQuestion.parseQuestion(byteBuffer).toString());



                        System.out.println();


                    } else if (key.isWritable()) { //TODO(migafgarcia): check if we can write and if the answer has been computed and send it
                        //write(key);
                        key.interestOps(SelectionKey.OP_READ);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
