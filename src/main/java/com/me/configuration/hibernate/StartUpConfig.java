package com.me.configuration.hibernate;

import javax.sql.DataSource;

import com.me.hello.entity.Event;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

/**
 * @author SStorozhev
 * @since 2/1/2016
 */
@Configuration
@PropertySource(value = {"classpath:hibernate.properties"})
public class StartUpConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartUpConfig.class);

    @Value("${jdbc.driverClassName}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        LOGGER.debug("Configuring HibernateTransactionManager using SessionFactory='{}'", sessionFactory);
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        return hibernateTransactionManager;
    }

    @Bean
    public SessionFactory sessionFactory() {
        LOGGER.debug("Configuring LocalSessionFactoryBean");
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource());
        sessionBuilder.addAnnotatedClass(Event.class);
        return sessionBuilder.buildSessionFactory();
    }

    @Bean
    public DataSource dataSource() {
        LOGGER.debug("Configuring DataSource");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}