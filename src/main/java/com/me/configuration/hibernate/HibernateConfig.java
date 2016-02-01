package com.me.configuration.hibernate;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

/**
 * @author SStorozhev
 * @since 2/1/2016
 */
@Configuration
public class HibernateConfig {

    @Bean
    public HibernateJpaSessionFactoryBean hibernateJpaSessionFactoryBean(EntityManagerFactory entityManagerFactory) {
        HibernateJpaSessionFactoryBean factory = new HibernateJpaSessionFactoryBean();
        factory.setEntityManagerFactory(entityManagerFactory);
        return factory;
    }

}
