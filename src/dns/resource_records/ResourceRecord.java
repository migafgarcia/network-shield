package dns.resource_records;

public interface ResourceRecord {
    /**
     * Return the resource record type
     * @return resource record type
     */
    DnsResourceRecordType getType();

    /**
     * Returns the length of the data in the resource record
     * @return length of the data in the resource record
     */
    int dataLength();
}
