package com.sifaki.webparser.prise;

/**
 * @author SStorozhev
 * @since 2/11/2016
 */
public enum PriceOperation implements NamesContainer {

    RANGE("-"),
    MULTIPLIER("*", "по");

    private String[] names;

    PriceOperation(String... name) {
        names = name;
    }

    @Override
    public String[] getNames() {
        return names;
    }

}
