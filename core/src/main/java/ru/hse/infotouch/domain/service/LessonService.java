package ru.hse.infotouch.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.models.Employee;
import ru.hse.infotouch.domain.models.Lesson;
import ru.hse.infotouch.domain.models.Person;
import ru.hse.infotouch.ruz.api.RuzApiService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final RuzApiService apiService;
    private final PersonService personService;
    private final EmployeeService lecturerService;

    @Autowired
    public LessonService(RuzApiService apiService, PersonService personService, EmployeeService lecturerService) {
        this.apiService = apiService;
        this.personService = personService;
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

    public List<Lesson> getNowLecturerLessons(int lecturerId) {
        List<Lesson> todayLessons = getTodayLecturerLesson(lecturerId);

        return this.filterByNowTime(todayLessons);
    }


    public List<Lesson> getNowPersonLessons(UUID personId) {
        List<Lesson> todayLessons = getTodayPersonLessons(personId);

        return this.filterByNowTime(todayLessons);
    }

    public List<Lesson> getPersonLessonsAtDate(UUID personId, LocalDate date) {

        return this.getPersonLessons(personId,
                date,
                date);
    }

    public List<Lesson> getTodayPersonLessons(UUID personId) {
        return getPersonLessonsAtDate(personId, LocalDate.now());
    }

    public List<Lesson> getPersonLessons(UUID personId, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);

        Person person = personService.getById(personId);

        return person.getEmployees().stream()
                .map(Employee::getLecturerId)
                .filter(Objects::nonNull)
                .flatMap(lecturerId -> this.getLecturerLessons(lecturerId, startDate, endDate).stream())
                .collect(Collectors.toList());
    }


    private List<Lesson> filterByNowTime(List<Lesson> todayLessons) {
        LocalTime now = LocalTime.now();

        return todayLessons.stream()
                .filter(lesson -> now.isAfter(lesson.getBeginLesson()) && now.isBefore(lesson.getEndLesson()))
                .collect(Collectors.toList());
    }

}
