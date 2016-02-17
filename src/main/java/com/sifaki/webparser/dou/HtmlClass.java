package com.sifaki.webparser.dou;

/**
 * @author SStorozhev
 * @since 2/8/2016
 */
public enum HtmlClass {

    PAGE("page"),
    PAGE_HEAD("page-head"),
    EVENT("event"),
    DATE("date"),
    TITLE("title"),
    B_TYPO("b-typo"),
    B_POST_TAGS("b-post-tags"),
    EVENT_INFO("event-info"),
    EVENT_INFO_ROW("event-info-row"),
    INFO("info"),
    DD("dd");

    private String name;

    HtmlClass(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
