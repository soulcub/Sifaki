package com.sifaki.configuration.parser;

import com.sifaki.webparser.dou.DouHtmlParser;
import com.sifaki.webparser.geo.vk.CityVkParser;
import com.sifaki.webparser.prise.PriceParser;
import org.apache.commons.httpclient.HttpClient;
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
    public HttpClient httpClient() {
        return new HttpClient();
    }

    @Bean
    public CityVkParser cityVkParser(HttpClient httpClient) {
        return new CityVkParser(httpClient);
    }

    @Bean
    public DouHtmlParser douHtmlParser(PriceParser priceParser, CityVkParser cityVkParser) {
        return new DouHtmlParser(priceParser, cityVkParser);
    }

}
