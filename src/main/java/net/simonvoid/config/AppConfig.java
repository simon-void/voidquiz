package net.simonvoid.config;

import net.simonvoid.view.GuiWrapper;
import net.simonvoid.datasource.QuizProvider;
import net.simonvoid.view.swing.SwingGuiWrapper;
import net.simonvoid.datasource.XStreamQuizProvider;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Spring Dependency Injection Configuration.
 * @author Stephan Schröder
 */
@Configuration
public class AppConfig {
    //Beans with Singleton scope

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public QuizProvider quizProvider()
    {
        return new XStreamQuizProvider();
    }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public GuiWrapper guiWrapper()
    {
        return new SwingGuiWrapper();
    }
}
