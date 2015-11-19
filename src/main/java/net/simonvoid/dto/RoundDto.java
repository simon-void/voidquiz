package net.simonvoid.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by stephan on 18.11.2015.
 */
public class RoundDto {
    @XStreamAlias("Question")
    private QuestionDto question;
    @XStreamImplicit(itemFieldName = "Answer")
    private List<AnswerDto> answers = new LinkedList<>();

    public QuestionDto getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDto question) {
        this.question = question;
    }

    public List<AnswerDto> getAnswers() {
        return Collections.unmodifiableList(answers);
    }

    public int getCorrectAnswerIndex() {
        for(int i=0; i<answers.size(); i++) {
            if(answers.get(i).isCorrect()) {
                return i;
            }
        }
        throw new IllegalStateException("no correct answer for question "+question.getText());
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        final char newline = '\n';
        StringBuilder sb = new StringBuilder();
        sb.append(question.toString()).append(newline);
        for(AnswerDto answer: answers) {
            sb.append(answer.toString()).append(newline);
        }
        return sb.toString();
    }
}
