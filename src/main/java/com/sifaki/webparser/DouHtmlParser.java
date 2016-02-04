package com.sifaki.webparser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sifaki.db.entity.Event;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import static com.sifaki.utils.StringUtils.REPLACE_ALL_NUMBERS_REGEX;
import static com.sifaki.webparser.CssQuery.HREF;
import static java.util.stream.Collectors.toList;

/**
 * @author SStorozhev
 * @since 2/2/2016
 */
public class DouHtmlParser {

    private static final String DOU_URL = "http://dou.ua";
    private static final int PAGE_NUMBER_START_WITH = 2;
    private static final String CALENDAR_URL_ENDING = "/calendar";
    private static final String PAGE = "page";
    private static final String EVENT = "event";
    private static final String TITLE = "title";

    public List<Event> parseAllEvents() throws IOException {
        Document document = getDocument(DOU_URL + CALENDAR_URL_ENDING);

        final String lastPageUrlEnding = getLastPageUrlEnding(document);
        final Integer lastPageNumber = getLastPageNumber(document);
        final List<String> eventLinks = getEventLinks(document);
        for (Integer nextPageNumber = PAGE_NUMBER_START_WITH; nextPageNumber <= lastPageNumber; nextPageNumber++) {
            final String nextPageUrl = getNextPageUrl(lastPageUrlEnding, nextPageNumber);
            document = getDocument(nextPageUrl);
            final List<String> nextPageEventLinks = getEventLinks(document);
            eventLinks.addAll(nextPageEventLinks);
        }

        final List<Event> events = new ArrayList<>();
        for (String eventLink : eventLinks) {
            document = getDocument(eventLink);
            events.add(getAndParseEvent(document));
        }

        return events;
    }

    private Integer getLastPageNumber(Document document) {
        final String lastPageNumberString = document.
                select(CssQuery.ALL_SPAN_ELEMENTS_WITH_CLASS_ATTRIBUTE_NAMED.as(PAGE)).
                last().
                select(CssQuery.ALL_A_ELEMENTS_WITH_HREF_ATTRIBUTE.get()).
                text();
        return Integer.valueOf(lastPageNumberString);
    }

    private String getLastPageUrlEnding(Document document) {
        return document.
                select(CssQuery.ALL_SPAN_ELEMENTS_WITH_CLASS_ATTRIBUTE_NAMED.as(PAGE)).
                last().
                select(CssQuery.ALL_A_ELEMENTS_WITH_HREF_ATTRIBUTE.get()).
                attr(HREF);
    }

    private List<String> getEventLinks(Document document) {
        final Elements events = document.select(CssQuery.ALL_DIV_ELEMENTS_WITH_CLASS_ATTRIBUTE_NAMED.as(EVENT));
        final Elements eventTitles = events.select(CssQuery.ALL_DIV_ELEMENTS_WITH_CLASS_ATTRIBUTE_NAMED.as(TITLE));
        final Elements eventsLinks = eventTitles.select(CssQuery.ALL_A_ELEMENTS_WITH_HREF_ATTRIBUTE.get());
        return eventsLinks.
                stream().
                map(element -> element.absUrl(HREF)).
                collect(toList());
    }

    private Event getAndParseEvent(Document document) {
//        final Event event = new Event();
//        return document.select(CssQuery.ALL_DIV_ELEMENTS_WITH_CLASS_ATTRIBUTE_NAMED_EVENT.getSelect());
        return null;
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
