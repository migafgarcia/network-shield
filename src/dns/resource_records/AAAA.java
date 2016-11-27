package dns.resource_records;

import java.nio.ByteBuffer;

/**
 * Resource record for forward IPv6 requests
 */
public class AAAA implements ResourceRecordData {

    public AAAA() {

    }

    public static AAAA parse(ByteBuffer byteBuffer) {

    }

    @Override
    public void toBits(ByteBuffer byteBuffer) {

    }

    @Override
    public int length() {
        return 0;
    }
}
