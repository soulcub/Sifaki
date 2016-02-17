package com.sifaki.webparser;

import com.sifaki.webparser.dou.HtmlElement;
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
                        all(HtmlElement.A)
        );

        final HtmlElement expected = HtmlElement.A;
        assertEquals(expected.toString(), result);
    }

    @Test
    public void testSelectAllElementsWhichContainsText() {
        final String result = buildSelect(
                select().
                        all(HtmlElement.A).
                        withText(TEXT)
        );

        final String expected = HtmlElement.A + ":contains(" + TEXT + ")";
        assertEquals(expected, result);
    }

    @Test
    public void testSelectAllElementsWithAttributes() {
        final String result = buildSelect(
                select().
                        all(HtmlElement.A).
                        with(HtmlElement.HREF)
        );

        final String expected = HtmlElement.A + "[" + HtmlElement.HREF + "]";
        assertEquals(expected, result);
    }

    @Test
    public void testSelectAllElementsWithClassesQuery() {
        final String result = buildSelect(
                select().
                        all(HtmlElement.A).
                        with(HtmlClass.DATE, HtmlClass.EVENT).
                        with(HtmlClass.PAGE_HEAD)
        );

        final String expected = HtmlElement.A +
                "." + HtmlClass.DATE +
                ",." + HtmlClass.EVENT +
                ",." + HtmlClass.PAGE_HEAD;
        assertEquals(expected, result);
    }

    @Test
    public void testSelectAllElementsWhichHaveElementsQuery() {
        final String result = buildSelect(
                select().
                        all(HtmlElement.A).
                        have(HtmlElement.DIV, HtmlElement.HREF).
                        have(HtmlElement.CLASS)
        );

        final String expected = HtmlElement.A +
                ":has(" + HtmlElement.DIV + ")" +
                ":has(" + HtmlElement.HREF + ")" +
                ":has(" + HtmlElement.CLASS + ")";
        assertEquals(expected, result);
    }

    @Test
    public void testSelectAllElementsWhichHaveElementsWhichContainsTextQuery() {
        final String result = buildSelect(
                select().
                        all(HtmlElement.A).
                        have(
                                select().
                                all(HtmlElement.DIV).
                                        withText(TEXT)
                        )
        );

        final String expected = HtmlElement.A + ":has(" + HtmlElement.DIV + ":contains(" + TEXT + "))";
        assertEquals(expected, result);
    }

}
