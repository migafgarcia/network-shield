package dns.resource_records;

import java.nio.ByteBuffer;

/**
 * Resource record that stores a 32-bit IP address
 */
public class A implements ResourceRecordData{

    private short octet1;
    private short octet2;
    private short octet3;
    private short octet4;

    /**
     * Constructor
     *
     * @param octet1
     * @param octet2
     * @param octet3
     * @param octet4
     */
    public A(short octet1, short octet2, short octet3, short octet4) {
        this.octet1 = octet1;
        this.octet2 = octet2;
        this.octet3 = octet3;
        this.octet4 = octet4;
    }

    @Override
    public void toBits(ByteBuffer byteBuffer) {
        byteBuffer.put((byte) octet1);
        byteBuffer.put((byte) octet2);
        byteBuffer.put((byte) octet3);
        byteBuffer.put((byte) octet4);
    }



}
