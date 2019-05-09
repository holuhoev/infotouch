package ru.hse.infotouch.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.models.Lesson;
import ru.hse.infotouch.domain.service.LessonService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/lecturer")
    public ResponseEntity<List<Lesson>> getLecturerLessons(int lecturerId,
                                                           @RequestParam(name = "from") LocalDate from,
                                                           @RequestParam(name = "to") LocalDate to) {

        return ResponseEntity.ok(lessonService.getLecturerLessons(lecturerId, from, to));
    }

    @GetMapping(value = "/lecturer/date")
    public ResponseEntity<List<Lesson>> getLecturerLessonsAtDate(int lecturerId,
                                                                 @RequestParam(name = "date") LocalDate date) {

        return ResponseEntity.ok(lessonService.getLecturerLessonsAtDate(lecturerId, date));
    }

    @GetMapping(value = "/lecturer/today")
    public ResponseEntity<List<Lesson>> getLecturerLessonsToday(int lecturerId) {

        return ResponseEntity.ok(lessonService.getTodayLecturerLesson(lecturerId));
    }

    @GetMapping(value = "/lecturer/now")
    public ResponseEntity<List<Lesson>> getLecturerLessonNow(int lecturerId) {

        return ResponseEntity.ok(lessonService.getNowLecturerLessons(lecturerId));
    }


    @GetMapping("/person")
    public ResponseEntity<List<Lesson>> getPersonLessons(@RequestParam(name = "personId") UUID personId,
                                                         @RequestParam(name = "from") LocalDate from,
                                                         @RequestParam(name = "to") LocalDate to) {

        return ResponseEntity.ok(lessonService.getPersonLessons(personId, from, to));
    }


    @GetMapping(value = "/person/date")
    public ResponseEntity<List<Lesson>> getPersonLessonsAtDate(@RequestParam(name = "personId") UUID personId,
                                                               @RequestParam(name = "date") LocalDate date) {

        return ResponseEntity.ok(lessonService.getPersonLessonsAtDate(personId, date));
    }

    @GetMapping(value = "/person/today")
    public ResponseEntity<List<Lesson>> getPersonLessonsToday(@RequestParam(name = "personId") UUID personId) {

        return ResponseEntity.ok(lessonService.getTodayPersonLessons(personId));
    }

    @GetMapping(value = "/person/now")
    public ResponseEntity<List<Lesson>> getPersonLessonNow(@RequestParam(name = "personId") UUID personId) {

        return ResponseEntity.ok(lessonService.getNowPersonLessons(personId));
    }
}
