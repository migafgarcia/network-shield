package dns;

public class DnsQuestion extends DnsHeader {

    private String name;

    private DnsResourceRecord resourceRecord;

    private DnsQueryClass queryClass;

    public DnsQuestion(int messageId, DnsOpcode opcode, boolean authoritativeAnswer, boolean truncation, boolean recursionDesired, boolean recursionAvailable, DnsResponseCode responseCode, int nQuestions, int nAnswers, int nResourceRecords, int nAdditional, String name, DnsResourceRecord resourceRecord, DnsQueryClass queryClass) {
        super(messageId, opcode, authoritativeAnswer, truncation, recursionDesired, recursionAvailable, responseCode, nQuestions, nAnswers, nResourceRecords, nAdditional);
        this.name = name;
        this.resourceRecord = resourceRecord;
        this.queryClass = queryClass;
    }

    public static DnsQuestion parseQuestion() {

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
