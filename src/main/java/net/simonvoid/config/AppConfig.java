package net.simonvoid.config;

import net.simonvoid.provider.NameProvider;
import net.simonvoid.provider.impl.NameProviderImpl;
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

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public NameProvider nameProvider()
    {
        return new NameProviderImpl();
    }
}
