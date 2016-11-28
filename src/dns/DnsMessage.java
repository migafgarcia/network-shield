package dns;

import dns.resource_records.ResourceRecord;
import dns.section.DnsHeader;
import dns.section.DnsQuestion;

import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.Arrays;

public class DnsMessage {
    private DnsHeader header;
    private DnsQuestion[] questionSection;
    private ResourceRecord[] answerSection;
    private ResourceRecord[] authoritySection;
    private ResourceRecord[] additionalSection;

    public DnsMessage(DnsHeader header, DnsQuestion[] questionSection, ResourceRecord[] answerSection, ResourceRecord[] authoritySection, ResourceRecord[] additionalSection) {
        this.header = header;
        this.questionSection = questionSection;
        this.answerSection = answerSection;
        this.authoritySection = authoritySection;
        this.additionalSection = additionalSection;
    }

    public static DnsMessage parseMessage(ByteBuffer byteBuffer) throws ParseException {

        DnsHeader header = DnsHeader.parseHeader(byteBuffer);

        DnsQuestion[] questions = new DnsQuestion[header.getnQuestions()];
        ResourceRecord[] answers = new ResourceRecord[header.getnAnswers()];
        ResourceRecord[] authority = new ResourceRecord[header.getnAuthority()];
        ResourceRecord[] additional = new ResourceRecord[header.getnAdditional()];

        for(int i = 0; i < header.getnQuestions(); i++)
            questions[i] = DnsQuestion.parseQuestion(byteBuffer);

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
                ", questionSection=" + Arrays.toString(questionSection) +
                ", answerSection=" + Arrays.toString(answerSection) +
                ", authoritySection=" + Arrays.toString(authoritySection) +
                ", additionalSection=" + Arrays.toString(additionalSection) +
                '}';
    }
}
