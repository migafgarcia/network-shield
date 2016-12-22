package dns;

import dns.section.Header;
import dns.section.Question;
import dns.section.ResourceRecord;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Message {
    private Header header;
    private Question[] questionSection;
    private ResourceRecord[] answerSection;
    private ResourceRecord[] authoritySection;
    private ResourceRecord[] additionalSection;

    public Message(Header header, Question[] questionSection, ResourceRecord[] answerSection, ResourceRecord[] authoritySection, ResourceRecord[] additionalSection) {
        this.header = header;
        this.questionSection = questionSection;
        this.answerSection = answerSection;
        this.authoritySection = authoritySection;
        this.additionalSection = additionalSection;
    }

    public Message(Header header) {
        this.header = header;
        this.questionSection = new Question[header.nQuestions()];
        this.answerSection = new ResourceRecord[header.nAnswers()];
        this.authoritySection = new ResourceRecord[header.nAuthority()];
        this.additionalSection = new ResourceRecord[header.nAdditional()];
    }

    public static Message parseMessage(ByteBuffer byteBuffer) {

        Header header = Header.parseHeader(byteBuffer);

        Question[] questions = new Question[header.nQuestions()];
        ResourceRecord[] answers = new ResourceRecord[header.nAnswers()];
        ResourceRecord[] authority = new ResourceRecord[header.nAuthority()];
        ResourceRecord[] additional = new ResourceRecord[header.nAdditional()];

        for(int i = 0; i < header.nQuestions(); i++)
            questions[i] = Question.parseQuestion(byteBuffer);

        for(int i = 0; i < header.nAnswers(); i++)
            answers[i] = ResourceRecord.parseResourceRecord(byteBuffer);

        for(int i = 0; i < header.nAuthority(); i++)
            authority[i] = ResourceRecord.parseResourceRecord(byteBuffer);

        for(int i = 0; i < header.nAdditional(); i++)
            additional[i] = ResourceRecord.parseResourceRecord(byteBuffer);

        return new Message(header, questions, answers, authority, additional);
    }

    @Override
    public String toString() {
        return "Message{" +
                "header=" + header.toString() +
                ", questionSection=" + Arrays.toString(questionSection) +
                ", answerSection=" + Arrays.toString(answerSection) +
                ", authoritySection=" + Arrays.toString(authoritySection) +
                ", additionalSection=" + Arrays.toString(additionalSection) +
                '}';
    }

    public Header getHeader() {
        return header;
    }

    public Question getQuestion(int i) {
        return questionSection[i];
    }

    public ResourceRecord getAnswer(int i) {
        return answerSection[i];
    }

    public ResourceRecord getAuthority(int i) {
        return authoritySection[i];
    }

    public ResourceRecord getAdditional(int i) {
        return additionalSection[i];
    }

    public void setQuestion(int i, Question question) {
        if(i >= 0 && i < header.nQuestions())
            questionSection[i] = question;
    }

    public void setAnswer(int i, ResourceRecord resourceRecord) {
        if(i >= 0 && i < header.nAnswers())
            answerSection[i] = resourceRecord;
    }

    public void setAuthority(int i, ResourceRecord resourceRecord) {
        if(i >= 0 && i < header.nAuthority())
            authoritySection[i] = resourceRecord;
    }

    public void setAdditional(int i, ResourceRecord resourceRecord) {
        if(i >= 0 && i < header.nAdditional())
            additionalSection[i] = resourceRecord;
    }

    public void toBytes(ByteBuffer buffer) {
        this.header.toBytes(buffer);

        if(questionSection != null)
            for(Question q : questionSection)
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
