package com.sifaki.configuration.json;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SStorozhev
 * @since 2/17/2016
 */
@Configuration
public class JsonConfiguration {

    @Bean
    public Gson gson() {
        return new Gson();
    }

}
