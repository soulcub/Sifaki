package com.sifaki.configuration.parser;

import com.sifaki.webparser.DouHtmlParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SStorozhev
 * @since 2/2/2016
 */
@Configuration
public class ParserConfig {

    @Bean
    public DouHtmlParser douHtmlParser() {
        return new DouHtmlParser();
    }

}
