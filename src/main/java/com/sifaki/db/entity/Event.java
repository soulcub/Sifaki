package com.sifaki.db.entity;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;

/**
 * @author SStorozhev
 * @since 1/27/2016
 */
@Entity
@Table(name = "events")
public class Event {
    @Id
    private int id;
    private String title;
    private String imageLink;
    private DateTime dateTime;
    private String where;
    private int cost;
    private String description;
    private ArrayList<String> tags;

    public Event() {
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("imageLink", imageLink)
                .append("dateTime", dateTime)
                .append("where", where)
                .append("cost", cost)
                .append("description", description)
                .append("tags", tags)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event that = (Event) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.title, that.title) &&
                Objects.equal(this.imageLink, that.imageLink) &&
                Objects.equal(this.dateTime, that.dateTime) &&
                Objects.equal(this.where, that.where) &&
                Objects.equal(this.cost, that.cost) &&
                Objects.equal(this.description, that.description) &&
                Objects.equal(this.tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, title, imageLink, dateTime, where, cost,
                description, tags);
    }
}