package com.sifaki.webparser;

import com.sifaki.webparser.dou.Element;
import com.sifaki.webparser.dou.HtmlClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.sifaki.webparser.JsoupQueryBuilder.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class JsoupQueryBuilderTest {

    public static final String TEXT = "text";

    private String buildSelect(JsoupQueryBuilder builder) {
        final String result = builder.build();
        System.out.println("Result: " + result);
        return result;
    }

    @Test
    public void testSelectAllElements() {
        final String result = buildSelect(
                select().
                        all(Element.A)
        );

        final Element expected = Element.A;
        assertEquals(expected.toString(), result);
    }

    @Test
    public void testSelectAllElementsWhichContainsText() {
        final String result = buildSelect(
                select().
                        all(Element.A).
                        withText(TEXT)
        );

        final String expected = Element.A + ":contains(" + TEXT + ")";
        assertEquals(expected, result);
    }

    @Test
    public void testSelectAllElementsWithAttributes() {
        final String result = buildSelect(
                select().
                        all(Element.A).
                        with(Element.HREF)
        );

        final String expected = Element.A + "[" + Element.HREF + "]";
        assertEquals(expected, result);
    }

    @Test
    public void testSelectAllElementsWithClassesQuery() {
        final String result = buildSelect(
                select().
                        all(Element.A).
                        with(HtmlClass.DATE, HtmlClass.EVENT).
                        with(HtmlClass.PAGE_HEAD)
        );

        final String expected = Element.A +
                "." + HtmlClass.DATE +
                ",." + HtmlClass.EVENT +
                ",." + HtmlClass.PAGE_HEAD;
        assertEquals(expected, result);
    }

    @Test
    public void testSelectAllElementsWhichHaveElementsQuery() {
        final String result = buildSelect(
                select().
                        all(Element.A).
                        have(Element.DIV, Element.HREF).
                        have(Element.CLASS)
        );

        final String expected = Element.A +
                ":has(" + Element.DIV + ")" +
                ":has(" + Element.HREF + ")" +
                ":has(" + Element.CLASS + ")";
        assertEquals(expected, result);
    }

    @Test
    public void testSelectAllElementsWhichHaveElementsWhichContainsTextQuery() {
        final String result = buildSelect(
                select().
                        all(Element.A).
                        have(
                                select().
                                all(Element.DIV).
                                        withText(TEXT)
                        )
        );

        final String expected = Element.A + ":has(" + Element.DIV + ":contains(" + TEXT + "))";
        assertEquals(expected, result);
    }

}
