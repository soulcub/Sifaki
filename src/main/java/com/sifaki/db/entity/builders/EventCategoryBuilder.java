package com.sifaki.db.entity.builders;

import com.sifaki.db.entity.EventCategory;

/**
 * @author SStorozhev
 * @since 4/22/2016
 */
public class EventCategoryBuilder {

    private Integer id;
    private String name;
    private String description;

    private EventCategoryBuilder() {
    }

    public static EventCategoryBuilder anEventCategory() {
        return new EventCategoryBuilder();
    }

    public EventCategoryBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public EventCategoryBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public EventCategoryBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public EventCategoryBuilder but() {
        return anEventCategory().withId(id).withName(name).withDescription(description);
    }

    public EventCategory build() {
        EventCategory eventCategory = new EventCategory();
        eventCategory.setId(id);
        eventCategory.setName(name);
        eventCategory.setDescription(description);
        return eventCategory;
    }

}
