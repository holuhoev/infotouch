package ru.hse.infotouch.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Strings {

    private Strings() {
        throw new AssertionError();
    }

    public static String removeRedundantSpace(String searchString) {
        return Arrays.stream(searchString.split(" "))
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(" "));
    }

}
