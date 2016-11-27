package dns;

import dns.section.DnsAnswer;
import dns.section.DnsHeader;
import dns.section.DnsQuestion;

import java.nio.ByteBuffer;
import java.text.ParseException;

public class DnsMessage {
    private DnsHeader header;
    private DnsQuestion[] questionSection;
    private DnsAnswer[] answerSection;
    private DnsAnswer[] authoritySection;
    private DnsAnswer[] additionalSection;

    public DnsMessage(DnsHeader header, DnsQuestion[] questionSection, DnsAnswer[] answerSection, DnsAnswer[] authoritySection, DnsAnswer[] additionalSection) {
        this.header = header;
        this.questionSection = questionSection;
        this.answerSection = answerSection;
        this.authoritySection = authoritySection;
        this.additionalSection = additionalSection;
    }

    public static DnsMessage parseMessage(ByteBuffer byteBuffer) throws ParseException {

        DnsHeader header = DnsHeader.parseHeader(byteBuffer);

        DnsQuestion[] questions = new DnsQuestion[header.getnQuestions()];
        DnsAnswer[] answers = new DnsAnswer[header.getnAnswers()];
        DnsAnswer[] authority = new DnsAnswer[header.getnAuthority()];
        DnsAnswer[] additional = new DnsAnswer[header.getnAdditional()];

        for(int i = 0; i < header.getnQuestions(); i++)
            questions[i] = DnsQuestion.parseQuestion(byteBuffer);

        for(int i = 0; i < header.getnAnswers(); i++)
            answers[i] = DnsAnswer.parseAnswer(byteBuffer);

        for(int i = 0; i < header.getnAuthority(); i++)
            authority[i] = DnsAnswer.parseAnswer(byteBuffer);

        for(int i = 0; i < header.getnAdditional(); i++)
            additional[i] = DnsAnswer.parseAnswer(byteBuffer);

        return new DnsMessage(header, questions, answers, authority, additional);
    }

}
