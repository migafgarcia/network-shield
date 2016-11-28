package dns;

import dns.sections.Header;
import dns.sections.Query;
import dns.sections.ResourceRecord;

import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.Arrays;

public class DnsMessage {
    private Header header;
    private Query[] query;
    private ResourceRecord[] answerSection;
    private ResourceRecord[] authoritySection;
    private ResourceRecord[] additionalSection;

    public DnsMessage(Header header, Query[] query, ResourceRecord[] answerSection, ResourceRecord[] authoritySection, ResourceRecord[] additionalSection) {
        this.header = header;
        this.query = query;
        this.answerSection = answerSection;
        this.authoritySection = authoritySection;
        this.additionalSection = additionalSection;
    }

    public static DnsMessage parseMessage(ByteBuffer byteBuffer) throws ParseException {

        Header header = Header.parseHeader(byteBuffer);

        Query[] questions = new Query[header.getnQuestions()];
        ResourceRecord[] answers = new ResourceRecord[header.getnAnswers()];
        ResourceRecord[] authority = new ResourceRecord[header.getnAuthority()];
        ResourceRecord[] additional = new ResourceRecord[header.getnAdditional()];

        for(int i = 0; i < header.getnQuestions(); i++)
            questions[i] = Query.parseQuestion(byteBuffer);

        for(int i = 0; i < header.getnAnswers(); i++)
            answers[i] = ResourceRecord.parseResourceRecord(byteBuffer);

        for(int i = 0; i < header.getnAuthority(); i++)
            authority[i] = ResourceRecord.parseResourceRecord(byteBuffer);

        for(int i = 0; i < header.getnAdditional(); i++)
            additional[i] = ResourceRecord.parseResourceRecord(byteBuffer);

        return new DnsMessage(header, questions, answers, authority, additional);
    }

    @Override
    public String toString() {
        return "DnsMessage{" +
                "header=" + header.toString() +
                ", query=" + Arrays.toString(query) +
                ", answerSection=" + Arrays.toString(answerSection) +
                ", authoritySection=" + Arrays.toString(authoritySection) +
                ", additionalSection=" + Arrays.toString(additionalSection) +
                '}';
    }
}
