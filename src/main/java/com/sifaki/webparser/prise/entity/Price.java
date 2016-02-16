package com.sifaki.webparser.prise.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.sifaki.webparser.prise.CurrencyType;

/**
 * @author SStorozhev
 * @since 2/11/2016
 */
public class Price {

    private Integer price;
    private CurrencyType currencyType;

    public Price(Integer price, CurrencyType currencyType) {
        this.price = price;
        this.currencyType = currencyType;
    }

    private Price(Builder builder) {
        setPrice(builder.price);
        setCurrencyType(builder.currencyType);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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
                .add("price", price)
                .add("currencyType", currencyType)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price that = (Price) o;

        return Objects.equal(this.price, that.price) &&
                Objects.equal(this.currencyType, that.currencyType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(price, currencyType);
    }

    public static final class Builder {
        private Integer price;
        private CurrencyType currencyType;

        private Builder() {
        }

        public Builder price(Integer val) {
            price = val;
            return this;
        }

        public Builder currencyType(CurrencyType val) {
            currencyType = val;
            return this;
        }

        public Price build() {
            return new Price(this);
        }
    }
}
