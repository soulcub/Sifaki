package com.sifaki.webparser.prise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sifaki.webparser.prise.entity.Price;
import javafx.util.Pair;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class PriceParserTest {

    public static final String FREE = "free";
    public static final Integer ZERO = 0;
    private final PriceParser priceParser = new PriceParser();

    @Test
    public void testParseNull() {
        final Price price = priceParser.parse(null);

        assertEquals(price.getPrice(), ZERO);
        assertEquals(price.getCurrencyType(), CurrencyType.FREE);
    }

    @Test
    public void testParseFree() {
        final Price price = priceParser.parse(FREE);

        assertEquals(price.getPrice(), ZERO);
        assertEquals(price.getCurrencyType(), CurrencyType.FREE);
    }

    public Object[] currencyTypeParameters() {
        final Map<CurrencyType, List<String>> mapFromEnum = NamesContainer.getMapFromEnum(CurrencyType.class);
        return mapFromEnum.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(value -> new Pair<>(value, entry.getKey())))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue)).entrySet().toArray();
    }

    @Parameters(method = "currencyTypeParameters")
    @Test
    public void testParsePriceWithNumberAndCurrency(Map.Entry<String, CurrencyType> currencyTypes) {
        final Integer price = 4100;
        final CurrencyType currencyType = currencyTypes.getValue();
        final String stringToParse = String.format("%d %s.", price, currencyTypes.getKey());
        final Price result = priceParser.parse(stringToParse);

        assertEquals(price, result.getPrice());
        assertEquals(currencyType, result.getCurrencyType());
    }

    @Parameters(method = "currencyTypeParameters")
    @Test
    public void testParsePriceWithWordsAndMultiplier(Map.Entry<String, CurrencyType> currencyTypes) {
        final int multiplier = 4;
        final int price = 4100;
        final CurrencyType currencyType = currencyTypes.getValue();
        final String stringToParse = String.format("%d платежа по %d %s.", multiplier, price, currencyTypes.getKey());
        final Price result = priceParser.parse(stringToParse);

        final Integer expected = multiplier * 4100;
        assertEquals(expected, result.getPrice());
        assertEquals(currencyType, result.getCurrencyType());
    }

    public Object[] currencyTypeAndPriceParameters() {
        final Map<CurrencyType, List<String>> currencyTypesMap = NamesContainer.getMapFromEnum(CurrencyType.class);
        final Map<PriceOperation, List<String>> priceOperationMap = NamesContainer.getMapFromEnum(PriceOperation.class);
        final Object[] currencyTypes = currencyTypesMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(value -> new Pair<>(value, entry.getKey())))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue)).entrySet().toArray();
        final Object[] priceOperations = priceOperationMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(value -> new Pair<>(value, entry.getKey())))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue)).entrySet().toArray();
        return getAllPossibleCombinations(currencyTypes, priceOperations);
    }

    private Object[] getAllPossibleCombinations(Object[] objects1, Object[] objects2) {
        ArrayList<Object> resultObjects = new ArrayList<>(objects1.length * objects2.length);
        for (Object o1 : objects1) {
            for (Object o2 : objects2) {
                resultObjects.add($(o1, o2));
            }
        }
        return resultObjects.toArray();
    }

    @Parameters(method = "currencyTypeAndPriceParameters")
    @Test
    public void testParsePriceWithRangeOrMultiplier(Map.Entry<String, CurrencyType> currencyTypes, Map.Entry<String, PriceOperation> priceOperations) {
        final int price1 = 10;
        final int price2 = 24;
        final CurrencyType currencyType = currencyTypes.getValue();
        final PriceOperation priceOperation = priceOperations.getValue();
        final String shortName = priceOperations.getKey();
        final String stringToParse = String.format("%s%d%s%s%d.", currencyTypes.getKey(), price1, shortName, currencyTypes.getKey(), price2);

        final Price result = priceParser.parse(stringToParse);

        Integer expected = 0;
        switch (priceOperation) {
            case MULTIPLIER:
                expected = 10 * 24;
                break;
            case RANGE:
                expected = (10 + 24) / 2;
                break;
        }
        assertEquals(expected, result.getPrice());
        assertEquals(currencyType, result.getCurrencyType());
    }

}
