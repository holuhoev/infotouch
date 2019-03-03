package ru.hse.infotouch.ruz.converter;


import java.time.LocalTime;

public class LocalTimeConverter implements AttributeConverter<LocalTime, String> {


    @Override
    public LocalTime convertToEntityAttribute(String ruzAttribute) {
        return LocalTime.parse(ruzAttribute);
    }
}
