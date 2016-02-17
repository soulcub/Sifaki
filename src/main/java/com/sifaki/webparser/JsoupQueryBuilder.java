package com.sifaki.webparser;

import java.util.ArrayList;
import java.util.Collections;

import com.sifaki.webparser.dou.HtmlElement;
import com.sifaki.webparser.dou.HtmlClass;

/**
 * @author SStorozhev
 * @since 2/8/2016
 */
public class JsoupQueryBuilder {

    private HtmlElement baseHtmlElement;
    private ArrayList<JsoupQueryBuilder> childElements = new ArrayList<>();
    private ArrayList<HtmlElement> attributes = new ArrayList<>();
    private ArrayList<HtmlClass> htmlClasses = new ArrayList<>();
    private String containedText;

    public static JsoupQueryBuilder select() {
        return new JsoupQueryBuilder();
    }

    public JsoupQueryBuilder all(HtmlElement htmlElement) {
        baseHtmlElement = htmlElement;
        return this;
    }

    public JsoupQueryBuilder with(HtmlClass... htmlClasses) {
        Collections.addAll(this.htmlClasses, htmlClasses);
        return this;
    }

    public JsoupQueryBuilder with(HtmlElement... attributes) {
        Collections.addAll(this.attributes, attributes);
        return this;
    }

    public JsoupQueryBuilder have(JsoupQueryBuilder... childElements) {
        Collections.addAll(this.childElements, childElements);
        return this;
    }

    public JsoupQueryBuilder have(HtmlElement... childHtmlElements) {
        for (HtmlElement childHtmlElement : childHtmlElements) {
            this.childElements.add(select().all(childHtmlElement));
        }
        return this;
    }

    public JsoupQueryBuilder withText(String containedText) {
        this.containedText = containedText;
        return this;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();

        if (!attributes.isEmpty()) {
            for (HtmlElement attribute : attributes) {
                builder.append('[').append(attribute).append("],");
            }
            builder.deleteCharAt(builder.lastIndexOf(","));
        }

        if (containedText != null) {
            builder.append(":contains(").append(containedText).append(")");
        }

        if (!htmlClasses.isEmpty()) {
            for (HtmlClass htmlClass : htmlClasses) {
                builder.append('.').append(htmlClass).append(',');
            }
            builder.deleteCharAt(builder.lastIndexOf(","));
        }

        if (!childElements.isEmpty()) {
            for (JsoupQueryBuilder childElement : childElements) {
                builder.append(":has(").append(childElement.build()).append(')');
            }
        }

        return baseHtmlElement.toString() + builder;
    }

}
