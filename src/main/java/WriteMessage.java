import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

/**
 *
 */
public class WriteMessage {

    private final SelectionKey key;
    private final SocketAddress address;
    private boolean sent;

    // This buffer wither holds the request or the sent
    private ByteBuffer buffer;

    public WriteMessage(SelectionKey key, SocketAddress sender, ByteBuffer requestBuffer) {
        this.key = key;
        this.address = sender;
        this.sent = false;
        this.buffer = requestBuffer;
    }

    public SelectionKey getKey() {
        return key;
    }

    public SocketAddress getAddress() {
        return address;
    }

    public boolean isSent() {
        return sent;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }
}
