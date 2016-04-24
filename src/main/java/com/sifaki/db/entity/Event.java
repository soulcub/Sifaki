package com.sifaki.db.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateTimeSerializer;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.sifaki.webparser.prise.CurrencyType;
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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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
    private Integer category;

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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
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

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event that = (Event) o;

        return Objects.equal(this.serialVersionUID, that.serialVersionUID) &&
                Objects.equal(this.id, that.id) &&
                Objects.equal(this.title, that.title) &&
                Objects.equal(this.imageLink, that.imageLink) &&
                Objects.equal(this.dateTime, that.dateTime) &&
                Objects.equal(this.coordinates, that.coordinates) &&
                Objects.equal(this.city, that.city) &&
                Objects.equal(this.cost, that.cost) &&
                Objects.equal(this.costCommentary, that.costCommentary) &&
                Objects.equal(this.description, that.description) &&
                Objects.equal(this.tags, that.tags) &&
                Objects.equal(this.sourceLink, that.sourceLink) &&
                Objects.equal(this.currencyType, that.currencyType) &&
                Objects.equal(this.category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(serialVersionUID, id, title, imageLink, dateTime, coordinates,
                city, cost, costCommentary, description, tags,
                sourceLink, currencyType, category);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("serialVersionUID", serialVersionUID)
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
                .add("currencyType", currencyType)
                .add("category", category)
                .toString();
    }
}
