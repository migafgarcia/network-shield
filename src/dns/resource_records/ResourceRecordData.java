package dns.resource_records;

import java.nio.ByteBuffer;

/**
 *
 */
public interface ResourceRecordData {
    void toBits(ByteBuffer byteBuffer);

    static ResourceRecordData parse(ByteBuffer byteBuffer, DnsResourceRecordType type) {

        switch(type) {
            case A:
                return new A(byteBuffer.get(), byteBuffer.get(), byteBuffer.get(), byteBuffer.get());
            default:
                return null;
        }


    }
}
