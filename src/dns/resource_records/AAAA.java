package dns.resource_records;

/**
 * Resource record for forward IPv6 requests
 */
public class AAAA implements ResourceRecord {
    @Override
    public DnsResourceRecordType getType() {
        return DnsResourceRecordType.AAAA;
    }

    @Override
    public int dataLength() {
        return 0;
    }
}
