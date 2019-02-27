package ru.hse.infotouch.terminal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.Lesson;
import ru.hse.infotouch.service.LessonService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/lesson/lecturer")
public class LectureLessonController {

    private final LessonService lessonService;

    @Autowired
    public LectureLessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public ResponseEntity<List<Lesson>> getLecturerLessons(int lecturerId,
                                                           @RequestParam(name = "from") @DateTimeFormat(iso = ISO.DATE) LocalDate from,
                                                           @RequestParam(name = "to") @DateTimeFormat(iso = ISO.DATE) LocalDate to) {

        return ResponseEntity.ok(lessonService.getLecturerLessons(lecturerId, from, to));
    }

    @GetMapping(value = "/date")
    public ResponseEntity<List<Lesson>> getLecturerLessonsAtDate(int lecturerId,
                                                                 @RequestParam(name = "date") @DateTimeFormat(iso = ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(lessonService.getLecturerLessonsAtDate(lecturerId, date));
    }

    @GetMapping(value = "/today")
    public ResponseEntity<List<Lesson>> getLecturerLessonsToday(int lecturerId) {

        return ResponseEntity.ok(lessonService.getTodayLecturerLesson(lecturerId));
    }

}
