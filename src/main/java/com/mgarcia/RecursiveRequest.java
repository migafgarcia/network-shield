package com.mgarcia;

import java.net.SocketAddress;
import java.nio.channels.SelectionKey;

/**
 *
 */
public class RecursiveRequest {

    private final short messageId;
    private final SelectionKey key;
    private final SocketAddress sender;
    private final long timestamp;

    public RecursiveRequest(short messageId, SelectionKey key, SocketAddress sender, long timestamp) {
        this.messageId = messageId;
        this.key = key;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public short getMessageId() {
        return messageId;
    }

    public SelectionKey getKey() {
        return key;
    }

    public SocketAddress getSender() {
        return sender;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "com.mgarcia.RecursiveRequest{" +
                "messageId=" + messageId +
                ", key=" + key +
                ", sender=" + sender +
                ", timestamp=" + timestamp +
                '}';
    }
}
