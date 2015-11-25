package net.simonvoid.data.model.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by stephan on 18.11.2015.
 */
@XStreamAlias("Quiz")
public class QuizDto {
    @XStreamAsAttribute
    private String name;
    @XStreamAsAttribute
    private int numberOfJokers;
    @XStreamImplicit(itemFieldName = "Round")
    private List<RoundDto> rounds = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfJokers() {
        return numberOfJokers;
    }

    public void setNumberOfJokers(int numberOfJokers) {
        this.numberOfJokers = numberOfJokers;
    }

    public List<RoundDto> getRounds() {
        return Collections.unmodifiableList(rounds);
    }

    public void setRounds(List<RoundDto> rounds) {
        this.rounds = rounds;
    }

    @Override
    public String toString() {
        final char newline = '\n';
        int index = 1;
        StringBuilder sb = new StringBuilder();
        sb.append("Quiz: ").append(name).append(", #joker(s): ").append(numberOfJokers).append(newline);
        for(RoundDto round: rounds) {
            sb.append(index++).append(") ").append(round.toString()).append(newline);
        }
        return sb.toString();
    }
}
