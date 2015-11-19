package net.simonvoid.config;

import static org.testng.Assert.assertNotNull;

import net.simonvoid.datasource.QuizProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by stephan on 18.11.2015.
 */
public class AppConfigTest {
    private ApplicationContext ctx;

    @BeforeMethod
    public void setup() {
        ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    public void testNameProviderBeanInitialised() {
        QuizProvider quizProvider = ctx.getBean(QuizProvider.class);

        assertNotNull(quizProvider, "quizProvider");
    }
}
