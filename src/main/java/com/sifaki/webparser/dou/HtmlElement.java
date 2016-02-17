package com.sifaki.webparser.dou;

/**
 * @author SStorozhev
 * @since 2/8/2016
 */
public enum HtmlElement {

    SPAN("span"),
    CLASS("class"),
    HREF("href"),
    A("a"),
    IMG("img"),
    DIV("div");

    private String name;

    HtmlElement(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
