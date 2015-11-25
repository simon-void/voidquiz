package net.simonvoid.data.service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import net.simonvoid.data.model.xml.AnswerDto;
import net.simonvoid.data.model.xml.QuestionDto;
import net.simonvoid.data.model.xml.QuizDto;
import net.simonvoid.data.model.xml.RoundDto;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by stephan on 18.11.2015.
 */
public class XStreamQuizProviderServiceImpl implements QuizProviderService {
    /**one configured XStream instance*/
    final private XStream xstream;

    public XStreamQuizProviderServiceImpl()
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
        String filecontent = loadFileContents("questions.xml");
        QuizDto quiz = deserializeXML(filecontent);
        return quiz;
    }

    private String loadFileContents(String path)
    throws IOException {
        ClassLoader classLoader = XStreamQuizProviderServiceImpl.class.getClassLoader();
        URL url = classLoader.getResource(path);
        String filecontent = IOUtils.toString(url.openStream(), "UTF-8");
        return filecontent;
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
