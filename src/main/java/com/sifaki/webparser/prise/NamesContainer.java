package com.sifaki.webparser.prise;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SStorozhev
 * @since 2/11/2016
 */
public interface NamesContainer {

    String[] getNames();

    static <T extends Enum & NamesContainer> Map<T, List<String>> getMapFromEnum(Class<T> namesContainerEnumClass) {
        final List<T> namesContainers = Arrays.asList(namesContainerEnumClass.getEnumConstants());
        Map<T, List<String>> resultMap = new HashMap<>();
        for (T namesContainer : namesContainers) {
            final String[] names = namesContainer.getNames();
            if (names != null) {
                resultMap.put(namesContainer, Arrays.asList(names));
            }
        }
        return resultMap;
    }
}
