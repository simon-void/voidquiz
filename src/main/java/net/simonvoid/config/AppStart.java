package net.simonvoid.config;

import net.simonvoid.dto.QuizDto;
import net.simonvoid.provider.QuizProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by stephan on 18.11.2015.
 */
public class AppStart {
    public static void main(String[] args) {
        //init Spring Dependency Injection and instanciate the gui provider
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        QuizProvider quizProvider = ctx.getBean(QuizProvider.class);

        QuizDto quiz = quizProvider.getQuiz();
        System.out.println("-------------------------------");
        System.out.println(quiz.toString());
        System.out.println("-------------------------------");
    }
}
