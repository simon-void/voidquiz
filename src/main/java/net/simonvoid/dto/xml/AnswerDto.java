package net.simonvoid.dto.xml;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

/**
 * Created by stephan on 18.11.2015.
 */
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"text"})
public class AnswerDto {
    @XStreamAsAttribute
    private boolean isCorrect = false;
    private String text;

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(isCorrect) {
            sb.append("(*) ");
        }else {
            sb.append("    ");
        }
        sb.append(text);
        return sb.toString();
    }
}
