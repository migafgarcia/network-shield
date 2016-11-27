package dns.section;

import dns.resource_records.DnsResourceRecordsClass;
import dns.resource_records.DnsResourceRecordType;

import java.nio.ByteBuffer;

public class DnsQuestion {

    private String name;

    private DnsResourceRecordType resourceRecord;

    private DnsResourceRecordsClass queryClass;

    public DnsQuestion(String name, DnsResourceRecordType resourceRecord, DnsResourceRecordsClass queryClass) {
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

        DnsResourceRecordsClass queryClass = DnsResourceRecordsClass.fromCode(queryClassCode);

        return new DnsQuestion(hostname.toString(), resourceRecordType, queryClass);


    }

    @Override
    public String toString() {
        return "\nDnsQuestion{" +
                "name='" + name + '\'' +
                ", resourceRecord=" + resourceRecord +
                ", queryClass=" + queryClass +
                '}';
    }
}
