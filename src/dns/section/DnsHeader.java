package dns.section;

import dns.DnsResponseCode;

public class DnsHeader {

    // Text source: http://www.zytrax.com/books/dns/ch15/

    /**
     * 16 bit message ID supplied by the requestion (the questioner) and reflected back unchanged by the responder
     * (answerer). Identifies the transaction.
     */
    private int messageId;

    /**
     * Query - Response bit. Set to 0 by the questioner (query) and to 1 in the response (answer).
     */
    private final boolean query = true;

    /**
     * Identifies the request/operation type.
     */
    private DnsOpcode opcode;

    /**
     * Authoritative Answer. Valid in responses only. Because of aliases multiple owners may exists so the AA bit
     * corresponds to the name which matches the query name, OR the first owner name in the answer section.
     */
    private boolean authoritativeAnswer;

    /**
     * TrunCation - specifies that this message was truncated due to length greater than that permitted on the
     * transmission channel. Set on all truncated messages except the last one.
     */
    private boolean truncation;

    /**
     * Recursion Desired - this bit may be set in a query and is copied into the response if recursion supported by this
     * Name Server. If Recursion is rejected by this Name Server, for example it has been configured as Authoritative
     * Only, the response (answer) does not have this bit set. Recursive query support is optional.
     */
    private boolean recursionDesired;

    /**
     * Recursion Available - this bit is valid in a response (answer) and denotes whether recursive query support is
     * available (1) or not (0) in the name server.
     */
    private boolean recursionAvailable;

    /**
     * Identifies the response type to the query. Ignored on a request (question).
     */
    private DnsResponseCode responseCode;

    /**
     * Unsigned 16 bit integer specifying the number of entries in the Question Section.
     */
    private int nQuestions;

    /**
     * Unsigned 16 bit integer specifying the number of resource records in the Answer Section. May be 0 in which case
     * no answer record is present in the message.
     */
    private int nAnswers;

    /**
     * Unsigned 16 bit integer specifying the number of name server resource records in the Authority Section. May be 0
     * in which case no authority record(s) is(are) present in the message.
     */
    private int nResourceRecords;

    /**
     * Unsigned 16 bit integer specifying the number of resource records in the Additional Section. May be 0 in which
     * case no addtional record(s) is(are) present in the message.
     */
    private int nAdditional;

    public DnsHeader(int messageId, DnsOpcode opcode, boolean authoritativeAnswer, boolean truncation, boolean recursionDesired, boolean recursionAvailable, DnsResponseCode responseCode, int nQuestions, int nAnswers, int nResourceRecords, int nAdditional) {
        this.messageId = messageId;
        this.opcode = opcode;
        this.authoritativeAnswer = authoritativeAnswer;
        this.truncation = truncation;
        this.recursionDesired = recursionDesired;
        this.recursionAvailable = recursionAvailable;
        this.responseCode = responseCode;
        this.nQuestions = nQuestions;
        this.nAnswers = nAnswers;
        this.nResourceRecords = nResourceRecords;
        this.nAdditional = nAdditional;
    }

    @Override
    public String toString() {
        return "DnsHeader{" +
                "messageId=" + messageId +
                ", query=" + query +
                ", opcode=" + opcode +
                ", authoritativeAnswer=" + authoritativeAnswer +
                ", truncation=" + truncation +
                ", recursionDesired=" + recursionDesired +
                ", recursionAvailable=" + recursionAvailable +
                ", responseCode=" + responseCode +
                ", nQuestions=" + nQuestions +
                ", nAnswers=" + nAnswers +
                ", nResourceRecords=" + nResourceRecords +
                ", nAdditional=" + nAdditional +
                '}';
    }
}

