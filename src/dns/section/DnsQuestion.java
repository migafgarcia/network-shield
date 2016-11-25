package dns.section;

import java.nio.ByteBuffer;

public class DnsQuestion {

    private String name;

    private DnsResourceRecordType resourceRecord;

    private DnsQueryClass queryClass;

    public DnsQuestion(String name, DnsResourceRecordType resourceRecord, DnsQueryClass queryClass) {
        this.name = name;
        this.resourceRecord = resourceRecord;
        this.queryClass = queryClass;
    }

    public static DnsQuestion parseQuestion(ByteBuffer byteBuffer) {
        byte labelSize;

        StringBuilder hostname = new StringBuilder();

        while((labelSize = byteBuffer.get()) != 0) {
            for (int i = 0; i < labelSize; i++)
                hostname.append((char) byteBuffer.get());
            hostname.append('.');
        }


        short resourceRecordTypeCode = byteBuffer.getShort();

        DnsResourceRecordType resourceRecordType = DnsResourceRecordType.fromCode(resourceRecordTypeCode);

        short queryClassCode = byteBuffer.getShort();

        DnsQueryClass queryClass = DnsQueryClass.fromCode(queryClassCode);

        return new DnsQuestion(hostname.toString(), resourceRecordType, queryClass);


    }

    @Override
    public String toString() {
        return super.toString() + "\nDnsQuestion{" +
                "name='" + name + '\'' +
                ", resourceRecord=" + resourceRecord +
                ", queryClass=" + queryClass +
                '}';
    }
}
