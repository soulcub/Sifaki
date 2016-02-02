package com.sifaki.webparser;

/**
 * @author SStorozhev
 * @since 2/2/2016
 */
public enum CssQuery {
    ALL_A_ELEMENTS_WITH_HREF_ATTRIBUTE("a[href]"),
    ALL_SPAN_ELEMENTS_WITH_CLASS_ATTRIBUTE_NAMED_PAGE("span[class=page]"),
    ALL_DIV_ELEMENTS_WITH_CLASS_ATTRIBUTE_NAMED_EVENT("div[class=event]");

    private String select;

    CssQuery(String select) {
        this.select = select;
    }

    public String getSelect() {
        return select;
    }
}
