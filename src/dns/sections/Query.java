package dns.sections;

import dns.resource_records.DnsResourceRecordClass;
import dns.resource_records.DnsResourceRecordType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class Query {

    private String[] labels;

    private DnsResourceRecordType resourceRecordType;

    private DnsResourceRecordClass resourceRecordClass;

    public Query(String[] labels, DnsResourceRecordType resourceRecordType, DnsResourceRecordClass resourceRecordClass) {
        this.labels = labels;
        this.resourceRecordType = resourceRecordType;
        this.resourceRecordClass = resourceRecordClass;
    }

    public static Query parseQuestion(ByteBuffer byteBuffer) {
        byte labelSize;

        StringBuilder current = new StringBuilder();

        ArrayList<String> labelsList = new ArrayList<>();

        while((labelSize = byteBuffer.get()) != 0) {
            for (int i = 0; i < labelSize; i++)
                current.append((char) byteBuffer.get()); // TODO(migafgarcia): Beware when casting - Gandalf
            labelsList.add(current.toString());
            current.setLength(0);
        }

        String[] labels = labelsList.toArray(new String[labelsList.size()]);

        short resourceRecordTypeCode = byteBuffer.getShort();

        DnsResourceRecordType resourceRecordType = DnsResourceRecordType.fromCode(resourceRecordTypeCode);

        short queryClassCode = byteBuffer.getShort();

        DnsResourceRecordClass queryClass = DnsResourceRecordClass.fromCode(queryClassCode);

        return new Query(labels, resourceRecordType, queryClass);

    }

    public void toBytes(ByteBuffer byteBuffer) {

        for(String label : labels) {
            byteBuffer.put((byte) label.length()); // TODO(migafgarcia): Beware when casting - Gandalf
            byteBuffer.put(label.getBytes());
        }

        byteBuffer.put((byte) 0);

        byteBuffer.putShort(resourceRecordType.toCode());

        byteBuffer.putShort(resourceRecordClass.toCode());




        // TODO(migafgarcia): implement this
    }

    @Override
    public String toString() {
        return "\nQuery{" +
                "labels='" + Arrays.toString(labels) + '\'' +
                ", resourceRecordType=" + resourceRecordType +
                ", resourceRecordClass=" + resourceRecordClass +
                '}';
    }
}
