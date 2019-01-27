package ru.hse.infotouch.core.ruz.util;


import ru.hse.infotouch.core.domain.Lesson;

import java.util.List;

public interface LessonParser {
    List<Lesson> parse(List<Lesson> lessons);
}
