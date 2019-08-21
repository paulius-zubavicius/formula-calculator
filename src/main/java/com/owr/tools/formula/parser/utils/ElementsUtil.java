package com.owr.tools.formula.parser.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ElementsUtil {

    public static <E> String listToString(List<E> list) {
       return listToString(list, " ");
    }

    public static <E> String listToString(List<E> list, String sep) {
        return listToString(list, sep, e -> e.toString());
    }

    public static <E> String listToString(List<E> list, Function<E, String> valueFunction) {
        return listToString(list, " ",valueFunction);
    }

    public static <E> String listToString(List<E> list, String sep, Function<E, String> valueFunction) {

        if (list == null) return "";

        return list.stream()
                .map(valueFunction)
                .collect(Collectors.joining(sep));
    }

}
