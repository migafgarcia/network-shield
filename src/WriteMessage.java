import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

/**
 *
 */
public class WriteMessage {

    private final SelectionKey key;
    private final SocketAddress address;
    private boolean response;

    // This buffer wither holds the request or the response
    private final ByteBuffer buffer;

    public WriteMessage(SelectionKey key, SocketAddress sender, ByteBuffer requestBuffer) {
        this.key = key;
        this.address = sender;
        this.response = false;
        this.buffer = requestBuffer;
    }

    public SelectionKey getKey() {
        return key;
    }

    public SocketAddress getAddress() {
        return address;
    }

    public boolean isResponse() {
        return response;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }
}
