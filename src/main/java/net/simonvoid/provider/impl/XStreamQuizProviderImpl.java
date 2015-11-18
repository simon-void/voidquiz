package net.simonvoid.provider.impl;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import net.simonvoid.dto.AnswerDto;
import net.simonvoid.dto.QuestionDto;
import net.simonvoid.dto.QuizDto;
import net.simonvoid.dto.RoundDto;
import net.simonvoid.provider.QuizProvider;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by stephan on 18.11.2015.
 */
public class XStreamQuizProviderImpl implements QuizProvider {
    /**one configured XStream instance*/
    final private XStream xstream;

    public XStreamQuizProviderImpl()
    {
        //configure one XStream instance to process all DTO classes
        xstream = new XStream(new PureJavaReflectionProvider());
        xstream.processAnnotations(
                new Class[] {
                        QuizDto.class,
                        RoundDto.class,
                        AnswerDto.class,
                        QuestionDto.class
                }
        );
    }

    @Override
    public QuizDto getQuiz() {
        try {
            QuizDto quiz = loadQuizFromFile();
            shuffleAnswers(quiz);
            return quiz;
        }catch (IOException e) {
            QuizDto emptyQuiz = new QuizDto();
            emptyQuiz.setName("no questions found");
            return emptyQuiz;
        }
    }

    private QuizDto loadQuizFromFile()
    throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("questions.xml").getFile());
        String filecontent = FileUtils.readFileToString(file, "UTF-8");
        QuizDto quiz = deserializeXML(filecontent);
        return quiz;
    }

    //package-scoped to make it testable
    QuizDto deserializeXML(String xml) {
        QuizDto quiz = (QuizDto)xstream.fromXML(xml);
        return quiz;
    }

    //package-scoped to make it testable/mockable
    void shuffleAnswers(QuizDto quiz) {
        for(RoundDto round: quiz.getRounds()) {
            List<AnswerDto> shuffeledAnsers = new ArrayList<>(round.getAnswers());
            Collections.shuffle(shuffeledAnsers);
            round.setAnswers(shuffeledAnsers);
        }
    }
}