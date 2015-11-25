package net.simonvoid.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by stephan on 25.11.2015.
 */

@Configuration
@EnableTransactionManagement
public class DbConfig {
    @Bean @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public LocalSessionFactoryBean localSessionFactoryBean() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "net.simonvoid.data.model.orm" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DataSource dataSource() {
        System.setProperty("derby.system.home", "./voidquizHighscore");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
        dataSource.setUrl("jdbc:derby:myDB;create=true;user=me;password=mine");
//        dataSource.setUsername("me");
//        dataSource.setPassword("mine");
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
        properties.put("hibernate.hbm2ddl.auto","update");
        properties.put("hibernate.show_sql","false");
        properties.put("hibernate.format_sql", "false");
        return properties;
    }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }
}
