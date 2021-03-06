package ru.hse.infotouch.ruz.api;


import ru.hse.infotouch.domain.models.*;
import ru.hse.infotouch.ruz.util.JsonField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Предоставляет интерфейс взаимодействия с http://ruz.hse.ru
 * Для того, чтобы получать значение поля объекта, возвращаемого из RUZ API,
 * необходимо использовать аннотацию {@link JsonField}
 *
 * @author Evgeny Kholukhoev
 */
public interface RuzApiService {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.d");

    List<Lesson> getStudentLessons(Integer studentId, LocalDate fromDate, LocalDate toDate);

    List<Lesson> getLecturerLessons(Integer lecturerId, LocalDate fromDate, LocalDate toDate);

    List<Student> getStudents(final Integer groupOid);

    List<Group> getGroups();

    List<Lecturer> getLecturers(Integer chairId);

    List<Lecturer> getAllLecturers();

    List<Chair> getAllChairs();

    List<Faculty> getAllFaculties();

    List<Building> getAllBuildings();

    List<Room> getAllAuditoriums();
}
