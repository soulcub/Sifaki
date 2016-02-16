package com.sifaki.db.entity;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

/**
 * @author SStorozhev
 * @since 1/27/2016
 */
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    private String title;
    @Column(name = "imageLink")
    private String imageLink;
    @Column(name = "dateTime")
    private DateTime dateTime;
    private String where;
    private Integer cost;
    @Column(name = "costCommentary")
    private String costCommentary;
    private String description;
    private ArrayList<String> tags;
    @Column(name = "sourceLink")
    private String sourceLink;

    public Event() {
    }

    private Event(Builder builder) {
        setId(builder.id);
        setTitle(builder.title);
        setImageLink(builder.imageLink);
        setDateTime(builder.dateTime);
        setWhere(builder.where);
        setCost(builder.cost);
        setCostCommentary(builder.costCommentary);
        setDescription(builder.description);
        setTags(builder.tags);
        setSourceLink(builder.sourceLink);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
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

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("title", title)
                .add("imageLink", imageLink)
                .add("dateTime", dateTime)
                .add("where", where)
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
                .append(this.where, rhs.where)
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
                .append(where)
                .append(cost)
                .append(costCommentary)
                .append(description)
                .append(tags)
                .append(sourceLink)
                .toHashCode();
    }

    public static final class Builder {
        private int id;
        private String title;
        private String imageLink;
        private DateTime dateTime;
        private String where;
        private Integer cost;
        private String costCommentary;
        private String description;
        private ArrayList<String> tags;
        private String sourceLink;

        private Builder() {
        }

        public Builder id(int val) {
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

        public Builder dateTime(DateTime val) {
            dateTime = val;
            return this;
        }

        public Builder where(String val) {
            where = val;
            return this;
        }

        public Builder cost(Integer val) {
            cost = val;
            return this;
        }

        public Builder costCommentary(String  val) {
            costCommentary = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder tags(ArrayList<String> val) {
            tags = val;
            return this;
        }

        public Builder sourceLink(String val) {
            sourceLink = val;
            return this;
        }

        public Event build() {
            return new Event(this);
        }
    }

}
