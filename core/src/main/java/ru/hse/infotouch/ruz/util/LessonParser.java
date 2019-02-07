package ru.hse.infotouch.ruz.util;


import ru.hse.infotouch.domain.Lesson;

import java.util.List;

public interface LessonParser {
    List<Lesson> parse(List<Lesson> lessons);
}
