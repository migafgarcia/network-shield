package dns.section;

import dns.resource_records_data.ResourceRecordData;

public class DnsAnswer {

    /**
     * The name being returned e.g. www or ns1.example.net If the name is in the same domain as the question then
     * typically only the host part (label) is returned, if not then a FQDN is returned.
     */
    private String name;

    /**
     *
     */
    private DnsResourceRecordType type;

    /**
     *
     */
    private DnsQueryClass queryClass;

    /**
     *
     */
    private int ttl;

    /**
     *
     */
    private ResourceRecordData resourceRecordData;

}
