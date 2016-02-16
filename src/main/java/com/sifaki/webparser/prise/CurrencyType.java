package com.sifaki.webparser.prise;

/**
 * @author SStorozhev
 * @since 2/11/2016
 */
public enum CurrencyType implements NamesContainer {

    UAH("uah", "грн", "гр"),
    USD("$", "usd", "дол"),
    FREE;

    private String[] names;

    CurrencyType() {
    }

    CurrencyType(String... name) {
        names = name;
    }

    @Override
    public String[] getNames() {
        return names;
    }

}
