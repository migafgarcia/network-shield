package dns.section;


import dns.resource_records.DnsResourceRecordClass;
import dns.resource_records.DnsResourceRecordType;
import dns.resource_records.ResourceRecord;

import java.nio.ByteBuffer;

public class DnsAnswer {


    /**
     * The name being returned e.g. www or ns1.example.net If the name is in the same domain as the question then
     * typically only the host part (label) is returned, if not then a FQDN is returned.
     */
    private final String name;

    private final DnsResourceRecordType resourceRecordType;

    private final DnsResourceRecordClass resourceRecordsClass;

    private final long ttl;

    private final ResourceRecord[] resourceRecords;

    public DnsAnswer(String name, DnsResourceRecordType resourceRecordType, DnsResourceRecordClass resourceRecordsClass, long ttl, ResourceRecord[] resourceRecords) {
        this.name = name;
        this.resourceRecordType = resourceRecordType;
        this.resourceRecordsClass = resourceRecordsClass;
        this.ttl = ttl;
        this.resourceRecords = resourceRecords;
    }

    public static DnsAnswer parseAnswer(ByteBuffer byteBuffer) {


        return null;
    }
}
