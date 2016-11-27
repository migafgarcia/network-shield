package dns.section;

import dns.codes.DnsOpcode;
import dns.codes.DnsResponseCode;
import utils.BitUtils;

import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.Arrays;

public class DnsHeader {

    // Text source: http://www.zytrax.com/books/dns/ch15/

    /**
     * 16 bit message ID supplied by the requestion (the questioner) and reflected back unchanged by the responder
     * (answerer). Identifies the transaction.
     */
    private final short messageId;

    /**
     * Query - Response bit. Set to 0 by the questioner (query) and to 1 in the response (answer).
     */
    private final boolean query;

    /**
     * Identifies the request/operation type.
     */
    private final DnsOpcode opcode;

    /**
     * Authoritative Answer. Valid in responses only. Because of aliases multiple owners may exists so the AA bit
     * corresponds to the name which matches the query name, OR the first owner name in the answer section.
     */
    private final boolean authoritativeAnswer;

    /**
     * TrunCation - specifies that this message was truncated due to length greater than that permitted on the
     * transmission channel. Set on all truncated messages except the last one.
     */
    private final boolean truncation;

    /**
     * Recursion Desired - this bit may be set in a query and is copied into the response if recursion supported by this
     * Name Server. If Recursion is rejected by this Name Server, for example it has been configured as Authoritative
     * Only, the response (answer) does not have this bit set. Recursive query support is optional.
     */
    private final boolean recursionDesired;

    /**
     * Recursion Available - this bit is valid in a response (answer) and denotes whether recursive query support is
     * available (1) or not (0) in the name server.
     */
    private final boolean recursionAvailable;

    /**
     * Identifies the response type to the query. Ignored on a request (question).
     */
    private final DnsResponseCode responseCode;

    /**
     * Unsigned 16 bit integer specifying the number of entries in the Question Section.
     */
    private final int nQuestions;

    /**
     * Unsigned 16 bit integer specifying the number of resource records in the Answer Section. May be 0 in which case
     * no answer record is present in the message.
     */
    private final int nAnswers;

    /**
     * Unsigned 16 bit integer specifying the number of name server resource records in the Authority Section. May be 0
     * in which case no authority record(s) is(are) present in the message.
     */
    private final int nAuthority;

    /**
     * Unsigned 16 bit integer specifying the number of resource records in the Additional Section. May be 0 in which
     * case no additional record(s) is(are) present in the message.
     */
    private final int nAdditional;

    /**
     * Default constructor
     *
     * @param messageId
     * @param opcode
     * @param authoritativeAnswer
     * @param truncation
     * @param recursionDesired
     * @param recursionAvailable
     * @param responseCode
     * @param nQuestions
     * @param nAnswers
     * @param nAuthority
     * @param nAdditional
     */
    public DnsHeader(short messageId, boolean query, DnsOpcode opcode, boolean authoritativeAnswer, boolean truncation, boolean recursionDesired, boolean recursionAvailable, DnsResponseCode responseCode, int nQuestions, int nAnswers, int nAuthority, int nAdditional) {
        this.messageId = messageId;
        this.query = query;
        this.opcode = opcode;
        this.authoritativeAnswer = authoritativeAnswer;
        this.truncation = truncation;
        this.recursionDesired = recursionDesired;
        this.recursionAvailable = recursionAvailable;
        this.responseCode = responseCode;
        this.nQuestions = nQuestions;
        this.nAnswers = nAnswers;
        this.nAuthority = nAuthority;
        this.nAdditional = nAdditional;
    }

    /**
     * 0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
     * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
     * |                      ID                       |
     * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
     * |QR|   Opcode  |AA|TC|RD|RA|   Z    |   RCODE   |
     * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
     * |                    QDCOUNT                    |
     * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
     * |                    ANCOUNT                    |
     * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
     * |                    NSCOUNT                    |
     * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
     * |                    ARCOUNT                    |
     * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
     *
     * @param byteBuffer
     * @return
     */
    public static DnsHeader parseHeader(ByteBuffer byteBuffer) throws ParseException {

        // get next 16 bits
        short messageId = byteBuffer.getShort();

        // get next 8 bits
        boolean[] boolBuffer = BitUtils.byteToBooleanArray(byteBuffer.get());

        boolean query = boolBuffer[0];

        int opcodeInt = BitUtils.booleanArrayToInt(Arrays.copyOfRange(boolBuffer, 1, 5));

        if (opcodeInt < 0 || opcodeInt > 15)
            throw new ParseException("Unrecognized opcode", byteBuffer.position());

        DnsOpcode opcode = DnsOpcode.fromCode(opcodeInt);

        boolean authoritativeAnswer = boolBuffer[5];

        boolean truncation = boolBuffer[6];

        boolean recursionDesired = boolBuffer[7];

        // get next 8 bits
        boolBuffer = BitUtils.byteToBooleanArray(byteBuffer.get());

        boolean recursionAvailable = boolBuffer[0];

        int responseCodeInt = BitUtils.booleanArrayToInt(Arrays.copyOfRange(boolBuffer, 4, 8));

        if (responseCodeInt < 0 || responseCodeInt > 15)
            throw new ParseException("Unrecognized response code", byteBuffer.position());

        DnsResponseCode responseCode = DnsResponseCode.fromCode(responseCodeInt);

        // get next 16 bits
        int nQuestions = byteBuffer.getShort();

        // get next 16 bits
        int nAnswers = byteBuffer.getShort();

        // get next 16 bits
        int nAuthority = byteBuffer.getShort();

        // get next 16 bits
        int nAdditional = byteBuffer.getShort();

        return new DnsHeader(messageId, query, opcode, authoritativeAnswer, truncation, recursionDesired, recursionAvailable, responseCode, nQuestions, nAnswers, nAuthority, nAdditional);
    }

    public void toBytes(ByteBuffer byteBuffer) {

        byteBuffer.putShort(messageId);



    }

    public short getMessageId() {
        return messageId;
    }

    public boolean isQuery() {
        return query;
    }

    public DnsOpcode getOpcode() {
        return opcode;
    }

    public boolean isAuthoritativeAnswer() {
        return authoritativeAnswer;
    }

    public boolean isTruncation() {
        return truncation;
    }

    public boolean isRecursionDesired() {
        return recursionDesired;
    }

    public boolean isRecursionAvailable() {
        return recursionAvailable;
    }

    public DnsResponseCode getResponseCode() {
        return responseCode;
    }

    public int getnQuestions() {
        return nQuestions;
    }

    public int getnAnswers() {
        return nAnswers;
    }

    public int getnAuthority() {
        return nAuthority;
    }

    public int getnAdditional() {
        return nAdditional;
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
                ", nAuthority=" + nAuthority +
                ", nAdditional=" + nAdditional +
                '}';
    }

}

