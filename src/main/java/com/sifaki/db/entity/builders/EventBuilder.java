package com.sifaki.db.entity.builders;

import com.sifaki.db.entity.Event;
import com.sifaki.webparser.prise.CurrencyType;
import org.joda.time.LocalDateTime;

/**
 * @author SStorozhev
 * @since 4/22/2016
 */
public class EventBuilder {

    private Integer id;
    private String title;
    private String imageLink;
    private LocalDateTime dateTime;
    private String coordinates;
    private String city;
    private Integer cost;
    private String costCommentary;
    private String description;
    private String tags;
    private String sourceLink;
    private CurrencyType currencyType;
    private Integer category;

    private EventBuilder() {
    }

    public static EventBuilder anEvent() {
        return new EventBuilder();
    }

    public EventBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public EventBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public EventBuilder withImageLink(String imageLink) {
        this.imageLink = imageLink;
        return this;
    }

    public EventBuilder withDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public EventBuilder withCoordinates(String coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public EventBuilder withCity(String city) {
        this.city = city;
        return this;
    }

    public EventBuilder withCost(Integer cost) {
        this.cost = cost;
        return this;
    }

    public EventBuilder withCostCommentary(String costCommentary) {
        this.costCommentary = costCommentary;
        return this;
    }

    public EventBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public EventBuilder withTags(String tags) {
        this.tags = tags;
        return this;
    }

    public EventBuilder withSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
        return this;
    }

    public EventBuilder withCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
        return this;
    }

    public EventBuilder withCategory(Integer category) {
        this.category = category;
        return this;
    }

    public Event build() {
        Event event = new Event();
        event.setId(id);
        event.setTitle(title);
        event.setImageLink(imageLink);
        event.setDateTime(dateTime);
        event.setCoordinates(coordinates);
        event.setCity(city);
        event.setCost(cost);
        event.setCostCommentary(costCommentary);
        event.setDescription(description);
        event.setTags(tags);
        event.setSourceLink(sourceLink);
        event.setCurrencyType(currencyType);
        event.setCategory(category);
        return event;
    }
}
