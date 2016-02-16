package com.sifaki.webparser.dou;

/**
 * @author SStorozhev
 * @since 2/8/2016
 */
public enum Element {

    SPAN("span"),
    CLASS("class"),
    HREF("href"),
    A("a"),
    IMG("img"),
    DIV("div");

    private String name;

    Element(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
