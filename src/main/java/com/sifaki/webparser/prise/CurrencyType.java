package com.sifaki.webparser.prise;

/**
 * @author SStorozhev
 * @since 2/11/2016
 */
public enum CurrencyType implements NamesContainer {

    FREE,
    USD("$", "usd", "дол"),
    EUR("eur", "євро", "евро"),
    UAH("uah", "грн", "гр");

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
