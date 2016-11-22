package dns;

import dns.section.DnsAnswer;
import dns.section.DnsHeader;
import dns.section.DnsQuestion;

public class DnsMessage {
    private DnsHeader header;
    private DnsQuestion questionSection;
    private DnsAnswer answerSection;
    private DnsAnswer authoritySection;
    private DnsAnswer additionalSection;

}
