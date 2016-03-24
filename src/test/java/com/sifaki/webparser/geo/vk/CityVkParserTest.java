package com.sifaki.webparser.geo.vk;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CityVkParserTest {
    private static final String WRONG_CITY = "TestCity";
    private static final String REAL_CITY = "Київ";

    @InjectMocks
    private CityVkParser cityVkParser = new CityVkParser(new HttpClient());

    @Test
    public void testParseOneWordRealCity() throws IOException {
        String resultCity = cityVkParser.parse(REAL_CITY);

        assertEquals(REAL_CITY, resultCity);
    }

    @Test
    public void testParseOneWordWrongCity() throws IOException {
        String resultCity = cityVkParser.parse(WRONG_CITY);

        assertEquals(null, resultCity);
    }

    @Test
    public void testParseAddressWithCityOnTheBeginning() throws IOException {
        String actual = cityVkParser.parse(REAL_CITY + " couple words to check");

        assertEquals(REAL_CITY, actual);
    }

    @Test
    public void testParseAddressWithCityOnTheEnd() throws IOException {
        String actual = cityVkParser.parse(WRONG_CITY + " couple words to check " + REAL_CITY);

        assertEquals(REAL_CITY, actual);
    }

    @Test
    public void testParseAddressWithWrongCityOnTheBeginningAndEnd() throws IOException {
        String actual = cityVkParser.parse(WRONG_CITY + " couple words to check " + WRONG_CITY);

        assertEquals(null, actual);
    }
}