package com.sifaki.webparser.prise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sifaki.utils.StringUtils;
import com.sifaki.webparser.prise.entity.Price;

import static com.sifaki.webparser.prise.NamesContainer.getMapFromEnum;

/**
 * @author SStorozhev
 * @since 2/11/2016
 */
public class PriceParser {

    public Price parse(String priceString) {
        if (priceString == null) {
            return createDefaultPrice();
        }
        final List<Integer> numbers = parseNumbersInString(priceString);
        final int numberOfNumbers = numbers.size();

        Integer price = 0;
        CurrencyType currencyType = CurrencyType.FREE;
        if (numberOfNumbers > 0) {
            currencyType = parseCurrencyType(priceString);
            switch (numberOfNumbers) {
                case 1:
                    price = numbers.iterator().next();
                    break;
                case 2:
                    final Map<PriceOperation, List<String>> priceOperationsMap = getMapFromEnum(PriceOperation.class);
                    PriceOperation priceOperation = parseAndGetFirstNamesContainer(priceString, priceOperationsMap);
                    if (priceOperation != null) {
                        switch (priceOperation) {
                            case MULTIPLIER:
                                price = Math.multiplyExact(numbers.get(0), numbers.get(1));
                                break;
                            case RANGE:
                                price = (numbers.get(0) + numbers.get(1)) / 2;
                                break;
                        }
                    }
                    break;
            }
        }

        return Price.newBuilder().
                price(price).
                currencyType(currencyType).
                build();
    }

    private Price createDefaultPrice() {
        return Price.newBuilder().
                price(0).
                currencyType(CurrencyType.FREE).
                build();
    }

    private CurrencyType parseCurrencyType(String priceString) {
        CurrencyType currencyType;
        final Map<CurrencyType, List<String>> currenciesMap = getMapFromEnum(CurrencyType.class);
        currencyType = parseAndGetFirstNamesContainer(priceString, currenciesMap);
        return currencyType;
    }

    private Integer parseAndGetFirstNumber(String priceString) {
        Pattern pattern = Pattern.compile(StringUtils.NUMBER_REGEX);
        final Matcher matcher = pattern.matcher(priceString);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        } else {
            return null;
        }
    }

    private List<Integer> parseNumbersInString(String priceString) {
        Pattern pattern = Pattern.compile(StringUtils.NUMBER_REGEX);
        final Matcher matcher = pattern.matcher(priceString);
        List<Integer> numbers = new ArrayList<>();
        while (matcher.find()) {
            final String numberString = matcher.group();
            final int number = Integer.parseInt(numberString);
            numbers.add(number);
        }
        return numbers;
    }

    private <T extends Enum & NamesContainer> T parseAndGetFirstNamesContainer(String stringToParse, Map<T, List<String>> namesContainers) {
        for (Map.Entry<T, List<String>> currency : namesContainers.entrySet()) {
            final List<String> names = currency.getValue();
            for (String name : names) {
                if (stringToParse.contains(name)) {
                    return currency.getKey();
                }
            }
        }
        return null;
    }

//    private CurrencyType findCostType(List<String> words) {
//        final ListIterator<String> wordsListIterator = words.listIterator();
//        while (wordsListIterator.hasNext()) {
//            final String word = wordsListIterator.next();
//            for (CurrencyType currencyType : CurrencyType.values()) {
//                final String[] tokensAfterSplitting = searchAndSplitByFoundNameable(word, currencyType);
//                if (tokensAfterSplitting != null) {
//                    if (tokensAfterSplitting.length == 0) {
//                        return currencyType;
//                    } else {
//                        for (String token : tokensAfterSplitting) {
//                            Pattern pattern = Pattern.compile("\\d+");
//                            final Matcher matcher = pattern.matcher(token);
//                            if (matcher.find()) {
//                                final String number = matcher.group(0);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return CurrencyType.FREE;
//    }

    /**
     * @param word          to parse
     * @param nameableClass to find in the {@code word}
     * @return {@code null} if no {@link NamesContainer} element was found and array of
     * {@link String}s which are left after splitting of the {@code word} by the {@link NamesContainer}.
     * Respectively if there was nothing except currency in the {@code word}, array will be empty.
     */
    private String[] searchAndSplitByFoundNameable(String word, Class<? extends NamesContainer> nameableClass) {

        return null;
    }

}
