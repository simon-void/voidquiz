package net.simonvoid.config;

import net.simonvoid.data.dao.HighscoreDao;
import net.simonvoid.data.dao.HighscoreDaoImpl;
import net.simonvoid.data.service.HighscoreService;
import net.simonvoid.data.service.HighscoreServiceImpl;
import net.simonvoid.view.GuiWrapper;
import net.simonvoid.data.service.QuizProviderService;
import net.simonvoid.view.swing.*;
import net.simonvoid.data.service.XStreamQuizProviderServiceImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

/**
 * Spring Dependency Injection Configuration.
 * @author Stephan Schröder
 */
@Configuration
@Import(DbConfig.class)
public class AppConfig {

    @Autowired
    private DbConfig dbConfig;
    //Beans with Singleton scope

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public QuizProviderService quizProvider()
    {
        return new XStreamQuizProviderServiceImpl();
    }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public HighscoreService highscoreService()
    {
        return new HighscoreServiceImpl();
    }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public HighscoreDao highscoreDao() {
        return new HighscoreDaoImpl();
    }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GuiWrapper guiWrapper()
    {
        return new SwingGuiWrapper();
    }

}
