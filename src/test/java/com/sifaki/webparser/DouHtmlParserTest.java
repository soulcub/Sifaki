package com.sifaki.webparser;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DouHtmlParserTest {
    private DouHtmlParser douHtmlParser = new DouHtmlParser();

    @Test
    public void testParse() throws IOException {
        douHtmlParser.parseAllEvents();
    }
}