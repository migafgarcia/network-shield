package dns.resource_records;

import java.nio.ByteBuffer;

/**
 * Created by mgarcia on 27-11-2016.
 */
public abstract class ResourceRecordData {

    public ResourceRecordData(int length) {

    }

    abstract void toBits(ByteBuffer byteBuffer);
    abstract int length();
}
