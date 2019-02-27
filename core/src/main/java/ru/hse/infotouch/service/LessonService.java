package ru.hse.infotouch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.Lesson;
import ru.hse.infotouch.ruz.api.RuzApiService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class LessonService {

    private final RuzApiService apiService;
    private final LecturerService lecturerService;

    @Autowired
    public LessonService(RuzApiService apiService, LecturerService lecturerService) {
        this.apiService = apiService;
        this.lecturerService = lecturerService;
    }


    public List<Lesson> getLecturerLessons(int lecturerId, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);

        if (!(lecturerService.isLecturerExists(lecturerId))) {
            throw new IllegalArgumentException("Lecturer with id " + lecturerId + " does not exist");
        }

        return apiService.getLecturerLessons(lecturerId, startDate, endDate);
    }

    public List<Lesson> getTodayLecturerLesson(int lecturerId) {
        LocalDate today = LocalDate.now();

        return getLecturerLessonsAtDate(lecturerId, today);
    }

    public List<Lesson> getLecturerLessonsAtDate(int lecturerId, LocalDate date) {

        return this.getLecturerLessons(lecturerId,
                date,
                date);
    }


}
