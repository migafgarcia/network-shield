package dns;

import dns.codes.Opcode;
import dns.codes.ResponseCode;
import dns.section.Header;
import dns.section.Question;
import dns.section.ResourceRecord;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;

public class Message {
    private Header header;
    private LinkedList<Question> questionSection;
    private LinkedList<ResourceRecord> answerSection;
    private LinkedList<ResourceRecord> authoritySection;
    private LinkedList<ResourceRecord> additionalSection;

    public Message(Header header, LinkedList<Question> questionSection, LinkedList<ResourceRecord> answerSection, LinkedList<ResourceRecord> authoritySection, LinkedList<ResourceRecord> additionalSection) {
        this.header = header;
        this.questionSection = questionSection;
        this.answerSection = answerSection;
        this.authoritySection = authoritySection;
        this.additionalSection = additionalSection;
    }

    public Message(short messageId, boolean query, Opcode opcode, boolean authoritativeAnswer, boolean truncation, boolean recursionDesired, boolean recursionAvailable, ResponseCode responseCode) {
        this.header = new Header(
                messageId,
                query,
                opcode,
                authoritativeAnswer,
                truncation,
                recursionDesired,
                recursionAvailable,
                responseCode,
                0,
                0,
                0,
                0);

        this.questionSection = new LinkedList<>();
        this.answerSection = new LinkedList<>();
        this.authoritySection = new LinkedList<>();
        this.additionalSection = new LinkedList<>();

    }

    public static Message parseMessage(ByteBuffer byteBuffer) {

        Header header = Header.parseHeader(byteBuffer);

        LinkedList<Question> questions = new LinkedList<>();
        LinkedList<ResourceRecord> answers = new LinkedList<>();
        LinkedList<ResourceRecord> authority = new LinkedList<>();
        LinkedList<ResourceRecord> additional = new LinkedList<>();

        for(int i = 0; i < header.nQuestions(); i++)
            questions.add(Question.fromBytes(byteBuffer));

        for(int i = 0; i < header.nAnswers(); i++)
            answers.add(ResourceRecord.fromBytes(byteBuffer));

        for(int i = 0; i < header.nAuthority(); i++)
            authority.add(ResourceRecord.fromBytes(byteBuffer));

        for(int i = 0; i < header.nAdditional(); i++)
            additional.add(ResourceRecord.fromBytes(byteBuffer));

        return new Message(header, questions, answers, authority, additional);
    }

    @Override
    public String toString() {
        return "Message{" +
                "header=" + header.toString() +
                ", questionSection=" + Arrays.toString(questionSection.toArray()) +
                ", answerSection=" + Arrays.toString(answerSection.toArray()) +
                ", authoritySection=" + Arrays.toString(authoritySection.toArray()) +
                ", additionalSection=" + Arrays.toString(additionalSection.toArray()) +
                '}';
    }

    public Header getHeader() {
        return header;
    }

    public Question getQuestion(int i) {
        if(i >= 0 && i < questionSection.size())
            return questionSection.get(i);
        else
            throw new IndexOutOfBoundsException("Question " + i + " doesn't exist");
    }

    public ResourceRecord getAnswer(int i) {
        if(i >= 0 && i < answerSection.size())
            return answerSection.get(i);
        else
            throw new IndexOutOfBoundsException("Answer " + i + " doesn't exist");
    }

    public ResourceRecord getAuthority(int i) {
        if(i >= 0 && i < authoritySection.size())
            return authoritySection.get(i);
        else
            throw new IndexOutOfBoundsException("Authority " + i + " doesn't exist");
    }

    public ResourceRecord getAdditional(int i) {
        if(i >= 0 && i < additionalSection.size())
            return additionalSection.get(i);
        else
            throw new IndexOutOfBoundsException("Additional " + i + " doesn't exist");
    }



    public void addQuestion(Question question) {
        questionSection.add(question);
        header.setnQuestions(questionSection.size());
    }

    public void addAnswer(ResourceRecord resourceRecord) {
        answerSection.add(resourceRecord);
        header.setnAnswers(answerSection.size());
    }

    public void addAuthority(ResourceRecord resourceRecord) {
        authoritySection.add(resourceRecord);
        header.setnAuthority(authoritySection.size());
    }

    public void addAdditional(ResourceRecord resourceRecord) {
        additionalSection.add(resourceRecord);
        header.setnAdditional(additionalSection.size());
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

    public LinkedList<Question> getQuestionSection() {
        return questionSection;
    }

    public LinkedList<ResourceRecord> getAnswerSection() {
        return answerSection;
    }

    public LinkedList<ResourceRecord> getAuthoritySection() {
        return authoritySection;
    }

    public LinkedList<ResourceRecord> getAdditionalSection() {
        return additionalSection;
    }
}
