package dns.resource_records;

/**
 * Resource record for forward IPv4 requests
 */
public class A implements ResourceRecord {

    @Override
    public DnsResourceRecordType getType() {
        return DnsResourceRecordType.A;
    }

    @Override
    public int dataLength() {
        return 0;
    }
}
