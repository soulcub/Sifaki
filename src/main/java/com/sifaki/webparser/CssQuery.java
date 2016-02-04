package com.sifaki.webparser;

/**
 * @author SStorozhev
 * @since 2/2/2016
 */
public enum CssQuery {
    ALL_A_ELEMENTS_WITH_HREF_ATTRIBUTE("a[" + CssQuery.HREF + "]"),
    ALL_SPAN_ELEMENTS_WITH_CLASS_ATTRIBUTE_NAMED("span[class=" + CssQuery.s + "]"),
    ALL_DIV_ELEMENTS_WITH_CLASS_ATTRIBUTE_NAMED("div[class=" + CssQuery.s + "]");

    public static final String HREF = "href";
    public static final String s = "%s";

    private String select;

    CssQuery(String select) {
        this.select = select;
    }

    public String get() {
        return select;
    }

    public String as(String name) {
        return select.replace(s, name);
    }
}
