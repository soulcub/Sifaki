package com.sifaki.configuration.parser;

import com.sifaki.webparser.dou.DouHtmlParser;
import com.sifaki.webparser.prise.PriceParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SStorozhev
 * @since 2/2/2016
 */
@Configuration
public class ParserConfig {

    @Bean
    public PriceParser priceParser() {
        return new PriceParser();
    }

    @Bean
    public DouHtmlParser douHtmlParser(PriceParser priceParser) {
        return new DouHtmlParser(priceParser);
    }

}
