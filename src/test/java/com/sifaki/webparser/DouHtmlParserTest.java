package com.sifaki.webparser;

import com.sifaki.webparser.dou.DouHtmlParser;
import com.sifaki.webparser.geo.vk.CityVkParser;
import com.sifaki.webparser.prise.PriceParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DouHtmlParserTest {

    private static final String DATE = "2016-02-11";

    @Mock
    private PriceParser priceParser;
    @Mock
    private CityVkParser cityVkParser;

    @InjectMocks
    private DouHtmlParser douHtmlParser;

    @Test
    public void testGetLastUrlBlock() {
        final String result = douHtmlParser.getLastUrlBlock("http://dou.ua/calendar/" + DATE + "/");

        assertEquals(DATE, result);
    }
}
