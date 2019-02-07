package ru.hse.infotouch.ruz.converter;


import ru.hse.infotouch.domain.Course;

import static java.util.Objects.requireNonNull;

public class CourseConverter implements AttributeConverter<Course, Integer> {
    @Override
    public Course convertToEntityAttribute(Integer ruzAttribute) {
        return Course.of(requireNonNull(ruzAttribute));
    }
}
