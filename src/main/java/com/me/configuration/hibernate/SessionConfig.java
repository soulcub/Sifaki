package com.me.configuration.hibernate;

import javax.sql.DataSource;

import com.me.hello.entity.Event;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

/**
 * @author SStorozhev
 * @since 2/1/2016
 */
@Configuration
public class SessionConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionConfig.class);

    @Autowired
    @Bean
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LOGGER.debug("Configuring SessionFactory using DataSource='{}'", dataSource);
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);

        sessionBuilder.addAnnotatedClass(Event.class);
        sessionBuilder.scanPackages("com.me.hello.entity");

        return sessionBuilder.buildSessionFactory();
    }
}