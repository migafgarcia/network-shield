package dns.section;

import dns.resource_records.ResourceRecordClass;
import dns.resource_records.ResourceRecordType;
import dns.resource_records.ResourceRecordData;
import utils.BitUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class ResourceRecord {
    private String[] labels;
    private ResourceRecordType resourceRecordType;
    private ResourceRecordClass resourceRecordClass;
    private long ttl;
    private int length;
    private ResourceRecordData resourceRecordData;

    public ResourceRecord(String[] labels, ResourceRecordType resourceRecordType, ResourceRecordClass resourceRecordClass, long ttl, int length, ResourceRecordData resourceRecordData) {
        this.labels = labels;
        this.resourceRecordType = resourceRecordType;
        this.resourceRecordClass = resourceRecordClass;
        this.ttl = ttl;
        this.length = length;
        this.resourceRecordData = resourceRecordData;
    }

    @Override
    public String toString() {
        return "ResourceRecord{" +
                "labels=" + Arrays.toString(labels) +
                ", resourceRecordType=" + resourceRecordType +
                ", resourceRecordClass=" + resourceRecordClass +
                ", ttl=" + ttl +
                ", length=" + length +
                ", resourceRecordData=" + resourceRecordData +
                '}';
    }

    public static ResourceRecord parseResourceRecord(ByteBuffer byteBuffer) {
        // either the top bits are 11 (compression) or the label format

        byte buf = byteBuffer.get();

        boolean[] bitBuffer = BitUtils.byteToBits(buf);

        // pointer format
        if(bitBuffer[0] && bitBuffer[1]) {
            System.err.println("Not implemented");
            return null;
        }

        StringBuilder current = new StringBuilder();

        ArrayList<String> labelsList = new ArrayList<>();

        while(buf != 0) {
            for (int i = 0; i < buf; i++)
                current.append((char) byteBuffer.get()); // TODO(migafgarcia): Beware when casting - Gandalf
            labelsList.add(current.toString());
            current.setLength(0);
            buf = byteBuffer.get();
        }
        String[] labels = labelsList.toArray(new String[labelsList.size()]);

        short resourceRecordTypeCode = byteBuffer.getShort();

        ResourceRecordType resourceRecordType = ResourceRecordType.fromCode(resourceRecordTypeCode);

        short queryClassCode = byteBuffer.getShort();

        ResourceRecordClass queryClass = ResourceRecordClass.fromCode(queryClassCode);

        int ttl = byteBuffer.getInt();

        int length = byteBuffer.getShort();

        ResourceRecordData resourceRecordData = ResourceRecordData.parse(byteBuffer, resourceRecordType);

        return new ResourceRecord(labels, resourceRecordType, queryClass, ttl, length, resourceRecordData);

    }

    public void toBytes(ByteBuffer byteBuffer) {

        for(String label : labels) {
            byteBuffer.put((byte) label.length()); // TODO(migafgarcia): Beware when casting - Gandalf
            byteBuffer.put(label.getBytes());
        }

        byteBuffer.put((byte) 0);

        byteBuffer.putShort(resourceRecordType.toCode());

        byteBuffer.putShort(resourceRecordClass.toCode());

        byteBuffer.putInt((int) ttl);

        byteBuffer.putShort((short) length);

        resourceRecordData.toBits(byteBuffer);
    }







}
