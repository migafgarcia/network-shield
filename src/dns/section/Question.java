package dns.section;

import dns.resource_records.ResourceRecordClass;
import dns.resource_records.ResourceRecordType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class Question {

    private String[] labels;

    private ResourceRecordType resourceRecordType;

    private ResourceRecordClass resourceRecordClass;

    public Question(String[] labels, ResourceRecordType resourceRecordType, ResourceRecordClass resourceRecordClass) {
        this.labels = labels;
        this.resourceRecordType = resourceRecordType;
        this.resourceRecordClass = resourceRecordClass;
    }

    public static Question parseQuestion(ByteBuffer byteBuffer) {
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

        ResourceRecordType resourceRecordType = ResourceRecordType.fromCode(resourceRecordTypeCode);

        short queryClassCode = byteBuffer.getShort();

        ResourceRecordClass queryClass = ResourceRecordClass.fromCode(queryClassCode);

        return new Question(labels, resourceRecordType, queryClass);

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

    public String getUrl() {
        return String.join(".", labels);
    }

    public String[] getLabels() {
        return labels;
    }

    public ResourceRecordType getResourceRecordType() {
        return resourceRecordType;
    }

    public ResourceRecordClass getResourceRecordClass() {
        return resourceRecordClass;
    }

    @Override
    public String toString() {
        return "\nQuestion{" +
                "labels='" + Arrays.toString(labels) + '\'' +
                ", resourceRecordType=" + resourceRecordType +
                ", resourceRecordClass=" + resourceRecordClass +
                '}';
    }
}
