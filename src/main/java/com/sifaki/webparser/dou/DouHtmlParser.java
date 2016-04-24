package com.sifaki.webparser.dou;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.sifaki.db.entity.Event;
import com.sifaki.db.entity.builders.EventBuilder;
import com.sifaki.webparser.geo.vk.CityVkParser;
import com.sifaki.webparser.prise.CurrencyType;
import com.sifaki.webparser.prise.PriceParser;
import com.sifaki.webparser.prise.entity.Price;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.sifaki.utils.StringUtils.NUMBER_REGEX;
import static com.sifaki.webparser.JsoupQueryBuilder.select;
import static com.sifaki.webparser.dou.HtmlClass.DATE;
import static com.sifaki.webparser.dou.HtmlClass.EVENT;
import static com.sifaki.webparser.dou.HtmlClass.EVENT_INFO;
import static com.sifaki.webparser.dou.HtmlClass.INFO;
import static com.sifaki.webparser.dou.HtmlClass.PAGE;
import static com.sifaki.webparser.dou.HtmlClass.PAGE_HEAD;
import static com.sifaki.webparser.dou.HtmlElement.A;
import static com.sifaki.webparser.dou.HtmlElement.DIV;
import static com.sifaki.webparser.dou.HtmlElement.SPAN;

/**
 * @author SStorozhev
 * @since 2/2/2016
 */
public class DouHtmlParser {

    private static Logger logger = LoggerFactory.getLogger(DouHtmlParser.class);
    private static final String DOU_URL = "http://dou.ua";
    private static final int PAGE_NUMBER_START_WITH = 2;
    private static final String CALENDAR_URL_ENDING = "/calendar";
    private static final String HREF = "href";
    private static final String SRC = "src";
    private static final String COORDINATES = "Место";
    private static final String COST = "Стоимость";

    private PriceParser priceParser;
    private CityVkParser cityVkParser;

    public DouHtmlParser(PriceParser priceParser, CityVkParser cityVkParser) {
        this.priceParser = priceParser;
        this.cityVkParser = cityVkParser;
    }

    /**
     * Parses all events using pages from {@value DOU_URL}{@value CALENDAR_URL_ENDING} page.
     *
     * @return {@link List} of parsed {@link Event}s.
     * @throws IOException Jsoap {@link Document} couldn't have been created.
     */
    public List<Event> parseAllEvents() throws IOException {
        final Document firstPageDocument = createJsoapDocument(DOU_URL + CALENDAR_URL_ENDING);

        final List<Map.Entry<LocalDateTime, String>> eventLinkWithDatePairs = parseEventLinkDatePairs(firstPageDocument);

        return parseEvents(eventLinkWithDatePairs);
    }

    private List<Event> parseEvents(List<Map.Entry<LocalDateTime, String>> eventLinkDatePairs) throws IOException {
        final List<Event> events = new ArrayList<>();
        for (Map.Entry<LocalDateTime, String> eventLinkDatePair : eventLinkDatePairs) {
            final String eventLink = eventLinkDatePair.getValue();
            final Document eventPageDocument = createJsoapDocument(eventLink);
            events.add(getAndParseEvent(eventPageDocument, eventLinkDatePair));
        }
        return events;
    }

    private List<Map.Entry<LocalDateTime, String>> parseEventLinkDatePairs(Document firstPageDocument) throws IOException {
        final List<Map.Entry<LocalDateTime, String>> eventLinkWithDatePairs = parseEventLinkWithDatePairs(firstPageDocument);

        final Integer lastPageNumber = parseLastPageNumber(firstPageDocument);
        for (Integer nextPageNumber = PAGE_NUMBER_START_WITH; nextPageNumber <= lastPageNumber; nextPageNumber++) {
            final String nextPageUrl = getNextPageUrl(parseLastPageUrlEnding(firstPageDocument), nextPageNumber);
            eventLinkWithDatePairs.addAll(parseEventLinkWithDatePairs(createJsoapDocument(nextPageUrl)));
        }
        return eventLinkWithDatePairs;
    }

    private Integer parseLastPageNumber(Document document) {
        final String lastPageNumberString = getLastPageElements(document).text();
        return Integer.valueOf(lastPageNumberString);
    }

    private String parseLastPageUrlEnding(Document document) {
        return getLastPageElements(document).attr(HREF);
    }

    private Elements getLastPageElements(Document document) {
        return document.
                select(select().
                        all(SPAN).
                        with(PAGE).
                        build()).
                last().
                select(select().
                        all(A).
                        with(HtmlElement.HREF).
                        build());
    }

    private List<Map.Entry<LocalDateTime, String>> parseEventLinkWithDatePairs(Document document) {
        logger.debug("Event link parsing has been started for {}.", document.baseUri());
        final Elements eventBlocks = document.select(select().
                all(DIV).
                with(INFO, EVENT).
                build());

        LocalDateTime dateTime = null;
        List<Map.Entry<LocalDateTime, String>> eventLinks = new ArrayList<>();
        for (Element eventBlock : eventBlocks) {
            final String className = eventBlock.className();
            if (StringUtils.equals(className, HtmlClass.INFO.toString())) {
                final Elements infoBlocks = eventBlock.select(select().
                        all(A).
                        with(DATE).
                        build());
                final Element firstInfoBlock = infoBlocks.first();
                final String url = firstInfoBlock.absUrl(HREF);
                final String date = getLastUrlBlock(url);
                dateTime = LocalDateTime.parse(date);
            } else if (StringUtils.equals(className, HtmlClass.EVENT.toString())) {
                Preconditions.checkNotNull(dateTime, "Date can't be null on this step. Check out html source.");
                final Elements eventTitles = eventBlock.select(select().
                        all(DIV).
                        with(HtmlClass.TITLE).
                        build());
                final Element eventLink = eventTitles.
                        select(select().all(A).with(HtmlElement.HREF).build()).
                        first();
                final String eventUrl = eventLink.absUrl(HREF);
                final AbstractMap.SimpleEntry<LocalDateTime, String> entry = new AbstractMap.SimpleEntry<>(dateTime, eventUrl);
                eventLinks.add(entry);
            }
        }

        return eventLinks;
    }

    public String getLastUrlBlock(String url) {
        return Iterables.getLast(Splitter.
                on('/').
                omitEmptyStrings().
                splitToList(url));
    }

    private Event getAndParseEvent(Document document, Map.Entry<LocalDateTime, String> eventLinkWithDatePair) throws IOException {
        final LocalDateTime eventDate = eventLinkWithDatePair.getKey();
        final String sourceLink = eventLinkWithDatePair.getValue();
        final String title = getTitle(document);

        final Elements eventInfo = getEventsInfo(document);
        final String imageLink = getEventImageLink(eventInfo);
        final String coordinates = parseEventInfoRow(eventInfo, COORDINATES);
        final String city = parseCity(coordinates);
        final String costCommentary = parseEventInfoRow(eventInfo, COST);
        final Price price = parseCost(costCommentary);
        final String description = parseDescription(document);
        final String tags = parseTags(document);

        return EventBuilder.anEvent().
                withTitle(title).
                withImageLink(imageLink).
                withDateTime(eventDate).
                withCoordinates(coordinates).
                withCity(city).
                withCost(price.getPrice()).
                withCostCommentary(costCommentary).
                withDescription(description).
                withTags(tags).
                withSourceLink(sourceLink).
                withCurrencyType(price.getCurrencyType()).
                build();
    }

    private String parseCity(String coordinates) throws IOException {
        return cityVkParser.parse(coordinates);
    }

    private String parseTags(Document document) {
        final Elements tagElements = document.select(
                select().
                        all(HtmlElement.DIV).
                        with(HtmlClass.B_POST_TAGS).
                        build()).select(
                select().
                        all(HtmlElement.A).
                        build());
        return tagElements.toString();
    }

    private String parseDescription(Document document) {
        return document.select(select().all(HtmlElement.ARTICLE).with(HtmlClass.B_TYPO).build()).html();
    }

    private Price parseCost(String costCommentary) {
        final Price defaultPrice = new Price(0, CurrencyType.FREE);
        return Optional.of(priceParser.parse(costCommentary)).or(defaultPrice);
    }

    private String parseEventInfoRow(Elements eventInfo, String containedText) {
        final Element eventInfoRowWithPlace = eventInfo.select(
                select().
                        all(DIV).
                        with(HtmlClass.EVENT_INFO_ROW).
                        have(select().
                                all(DIV).
                                withText(containedText)).
                        build()).
                first();
        if (eventInfoRowWithPlace != null) {
            return eventInfoRowWithPlace.select(
                    select().
                            all(DIV).
                            with(HtmlClass.DD).
                            build()).
                    text();
        }
        return null;
    }

    private String getEventImageLink(Elements eventInfo) {
        return eventInfo.select(
                select().
                        all(HtmlElement.IMG).
                        build()).
                first().
                absUrl(SRC);
    }

    private Elements getEventsInfo(Document document) {
        return document.select(
                select().
                        all(DIV).
                        with(EVENT_INFO).
                        build());
    }

    private String getTitle(Document document) {
        return document.select(
                select().
                        all(DIV).
                        with(PAGE_HEAD).
                        build()).
                text();
    }

    private String getNextPageUrl(String lastPageUrlEnding, Integer currentPageNumber) {
        final String nextPageEnding = lastPageUrlEnding.replaceFirst(NUMBER_REGEX, currentPageNumber.toString());
        return DOU_URL + nextPageEnding;
    }

    private Document createJsoapDocument(String url) throws IOException {
        Connection connection = Jsoup.connect(url);
        return connection.get();
    }

}
