package ru.hse.infotouch.ruz.converter;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeConverter implements AttributeConverter<LocalTime, String> {
    private final static String LESSON_TIME_PATTERN = "hh:ss";
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LESSON_TIME_PATTERN);

    @Override
    public LocalTime convertToEntityAttribute(String ruzAttribute) {
        return LocalTime.parse(ruzAttribute, formatter);
    }
}
