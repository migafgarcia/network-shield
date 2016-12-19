package dns;

import dns.section.Header;
import dns.section.Question;
import dns.section.ResourceRecord;

import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.Arrays;

public class Message {
    private Header header;
    private Question[] question;
    private ResourceRecord[] answerSection;
    private ResourceRecord[] authoritySection;
    private ResourceRecord[] additionalSection;

    public Message(Header header, Question[] question, ResourceRecord[] answerSection, ResourceRecord[] authoritySection, ResourceRecord[] additionalSection) {
        this.header = header;
        this.question = question;
        this.answerSection = answerSection;
        this.authoritySection = authoritySection;
        this.additionalSection = additionalSection;
    }

    public static Message parseMessage(ByteBuffer byteBuffer) {

        Header header = Header.parseHeader(byteBuffer);

        Question[] questions = new Question[header.getnQuestions()];
        ResourceRecord[] answers = new ResourceRecord[header.getnAnswers()];
        ResourceRecord[] authority = new ResourceRecord[header.getnAuthority()];
        ResourceRecord[] additional = new ResourceRecord[header.getnAdditional()];

        for(int i = 0; i < header.getnQuestions(); i++)
            questions[i] = Question.parseQuestion(byteBuffer);

        for(int i = 0; i < header.getnAnswers(); i++)
            answers[i] = ResourceRecord.parseResourceRecord(byteBuffer);

        for(int i = 0; i < header.getnAuthority(); i++)
            authority[i] = ResourceRecord.parseResourceRecord(byteBuffer);

        for(int i = 0; i < header.getnAdditional(); i++)
            additional[i] = ResourceRecord.parseResourceRecord(byteBuffer);

        return new Message(header, questions, answers, authority, additional);
    }

    @Override
    public String toString() {
        return "Message{" +
                "header=" + header.toString() +
                ", question=" + Arrays.toString(question) +
                ", answerSection=" + Arrays.toString(answerSection) +
                ", authoritySection=" + Arrays.toString(authoritySection) +
                ", additionalSection=" + Arrays.toString(additionalSection) +
                '}';
    }

    public Header getHeader() {
        return header;
    }

    public Question getQuestion(int i) {
        return question[i];
    }

    public ResourceRecord[] getAnswerSection() {
        return answerSection;
    }

    public ResourceRecord[] getAuthoritySection() {
        return authoritySection;
    }

    public ResourceRecord[] getAdditionalSection() {
        return additionalSection;
    }

    public void toBytes(ByteBuffer buffer) {
        this.header.toBytes(buffer);

        if(question != null)
            for(Question q : question)
                q.toBytes(buffer);
        if(answerSection != null)
            for(ResourceRecord r : answerSection)
                r.toBytes(buffer);
        if(authoritySection != null)
            for(ResourceRecord r : authoritySection)
                r.toBytes(buffer);
        if(additionalSection != null)
            for(ResourceRecord r : additionalSection)
                r.toBytes(buffer);




    }
}
