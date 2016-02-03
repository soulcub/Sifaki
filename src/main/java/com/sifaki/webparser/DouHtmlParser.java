package com.sifaki.webparser;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author SStorozhev
 * @since 2/2/2016
 */
public class DouHtmlParser {

    private static final String DOU_URL = "http://dou.ua";
    private static final int PAGE_NUMBER_START_WITH = 2;
    private static final String REPLACE_ALL_NUMBERS_REGEX = "\\d+";
    private static final String CALENDAR_URL_ENDING = "/calendar";
    private static final String HREF_ATTRIBUTE = "href";

    public Elements parseAllEvents() throws IOException {
        Document document = getDocument(DOU_URL + CALENDAR_URL_ENDING);

        final String lastPageUrlEnding = getLastPageUrlEnding(document);
        final Integer lastPageNumber = getLastPageNumber(document);
        final Elements events = getEvents(document);
        for (Integer nextPageNumber = PAGE_NUMBER_START_WITH; nextPageNumber <= lastPageNumber; nextPageNumber++) {
            final String nextPageUrl = getNextPageUrl(lastPageUrlEnding, nextPageNumber);
            document = getDocument(nextPageUrl);
            events.addAll(getEvents(document));
        }

        return events;
    }

    private Integer getLastPageNumber(Document document) {
        final String lastPageNumberString = document.
                select(CssQuery.ALL_SPAN_ELEMENTS_WITH_CLASS_ATTRIBUTE_NAMED_PAGE.getSelect()).
                last().
                select(CssQuery.ALL_A_ELEMENTS_WITH_HREF_ATTRIBUTE.getSelect()).
                text();
        return Integer.valueOf(lastPageNumberString);
    }

    private String getLastPageUrlEnding(Document document) {
        return document.
                select(CssQuery.ALL_SPAN_ELEMENTS_WITH_CLASS_ATTRIBUTE_NAMED_PAGE.getSelect()).
                last().
                select(CssQuery.ALL_A_ELEMENTS_WITH_HREF_ATTRIBUTE.getSelect()).
                attr(HREF_ATTRIBUTE);
    }

    private Elements getEvents(Document document) {
        return document.select(CssQuery.ALL_DIV_ELEMENTS_WITH_CLASS_ATTRIBUTE_NAMED_EVENT.getSelect());
    }

    private String getNextPageUrl(String lastPageUrlEnding, Integer currentPageNumber) {
        final String nextPageEnding = lastPageUrlEnding.replaceFirst(REPLACE_ALL_NUMBERS_REGEX, currentPageNumber.toString());
        return DOU_URL + nextPageEnding;
    }

    Document getDocument(String url) throws IOException {
        Connection connection = getConnection(url);
        return connection.get();
    }

    Connection getConnection(String url) {
        return Jsoup.connect(url);
    }

}
