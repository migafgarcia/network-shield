package dns.resource_records;

import java.nio.ByteBuffer;

/**
 * Resource record for forward IPv4 requests
 */
public class A implements ResourceRecordData {

    @Override
    public void toBits(ByteBuffer byteBuffer) {

    }

    @Override
    public int length() {
        return 0;
    }
}
