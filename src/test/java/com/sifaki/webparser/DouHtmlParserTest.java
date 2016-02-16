package com.sifaki.webparser;

import java.io.IOException;

import com.sifaki.webparser.dou.DouHtmlParser;
import com.sifaki.webparser.prise.PriceParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DouHtmlParserTest {

    public static final String DATE = "2016-02-11";

    @Mock
    private PriceParser priceParser;

    private DouHtmlParser douHtmlParser = new DouHtmlParser(priceParser);

    @Test
    public void testParse() throws IOException {
        douHtmlParser.parseAllEvents();
    }

    @Test
    public void testGetLastUrlBlock() {
        final String result = douHtmlParser.getLastUrlBlock("http://dou.ua/calendar/" + DATE + "/");

        assertEquals(DATE, result);
    }

}
