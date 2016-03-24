package com.sifaki.db.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;
import com.sifaki.webparser.prise.CurrencyType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * @author SStorozhev
 * @since 1/27/2016
 */
@Entity
@Table(name = "events")
public class Event implements Serializable {

    private static final long serialVersionUID = 6002807323968359900L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private String title;
    @Column(name = "image_link")
    private String imageLink;
    @Column(name = "date_time")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime dateTime;
    private String coordinates;
    private String city;
    private Integer cost;
    @Column(name = "cost_commentary")
    private String costCommentary;
    private String description;
    private String tags;
    @Id
    @Column(name = "source_link")
    private String sourceLink;
    @Column(name = "currency_type")
    private CurrencyType currencyType;

    public Event() {
    }

    private Event(Builder builder) {
        setId(builder.id);
        setTitle(builder.title);
        setImageLink(builder.imageLink);
        setDateTime(builder.dateTime);
        setCoordinates(builder.coordinates);
        setCity(builder.city);
        setCost(builder.cost);
        setCostCommentary(builder.costCommentary);
        setDescription(builder.description);
        setTags(builder.tags);
        setSourceLink(builder.sourceLink);
        setCurrencyType(builder.currencyType);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() { //Bad solution. TODO: Try to find the better one to directly persist csv.
        return Arrays.asList(tags.split(","));
    }

    public void setTags(List<String> tags) { //Bad solution. TODO: Try to find the better one to directly persist csv.
        if (tags == null) {
            return;
        }
        StringBuilder result = new StringBuilder();
        final Iterator<String> iterator = tags.iterator();
        while (iterator.hasNext()) {
            result.append(iterator.next());
            if (iterator.hasNext()) {
                result.append(",");
            }
        }
        this.tags = result.toString();
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public String getCostCommentary() {
        return costCommentary;
    }

    public void setCostCommentary(String costCommentary) {
        this.costCommentary = costCommentary;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("title", title)
                .add("imageLink", imageLink)
                .add("dateTime", dateTime)
                .add("coordinates", coordinates)
                .add("city", city)
                .add("cost", cost)
                .add("costCommentary", costCommentary)
                .add("description", description)
                .add("tags", tags)
                .add("sourceLink", sourceLink)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Event rhs = (Event) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .append(this.title, rhs.title)
                .append(this.imageLink, rhs.imageLink)
                .append(this.dateTime, rhs.dateTime)
                .append(this.coordinates, rhs.coordinates)
                .append(this.city, rhs.city)
                .append(this.cost, rhs.cost)
                .append(this.costCommentary, rhs.costCommentary)
                .append(this.description, rhs.description)
                .append(this.tags, rhs.tags)
                .append(this.sourceLink, rhs.sourceLink)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(title)
                .append(imageLink)
                .append(dateTime)
                .append(coordinates)
                .append(city)
                .append(cost)
                .append(costCommentary)
                .append(description)
                .append(tags)
                .append(sourceLink)
                .toHashCode();
    }

    public static final class Builder {
        private Integer id;
        private String title;
        private String imageLink;
        private LocalDateTime dateTime;
        private String coordinates;
        private String city;
        private Integer cost;
        private String costCommentary;
        private String description;
        private List<String> tags;
        private String sourceLink;
        private CurrencyType currencyType;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder imageLink(String val) {
            imageLink = val;
            return this;
        }

        public Builder dateTime(LocalDateTime val) {
            dateTime = val;
            return this;
        }

        public Builder coordinates(String val) {
            coordinates = val;
            return this;
        }

        public Builder city(String val) {
            city = val;
            return this;
        }

        public Builder cost(Integer val) {
            cost = val;
            return this;
        }

        public Builder costCommentary(String val) {
            costCommentary = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder tags(List<String> val) {
            tags = val;
            return this;
        }

        public Builder sourceLink(String val) {
            sourceLink = val;
            return this;
        }

        public Builder currencyType(CurrencyType val) {
            currencyType = val;
            return this;
        }

        public Event build() {
            return new Event(this);
        }
    }

}
