package ru.hse.infotouch.core.ruz.converter;


import ru.hse.infotouch.core.domain.Course;

import static java.util.Objects.requireNonNull;

public class CourseConverter implements AttributeConverter<Course, Integer> {
    @Override
    public Course convertToEntityAttribute(Integer ruzAttribute) {
        return Course.of(requireNonNull(ruzAttribute));
    }
}
