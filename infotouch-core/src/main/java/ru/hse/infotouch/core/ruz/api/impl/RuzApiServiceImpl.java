package ru.hse.infotouch.core.ruz.api.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.core.domain.*;
import ru.hse.infotouch.core.ruz.Endpoint;
import ru.hse.infotouch.core.ruz.Param;
import ru.hse.infotouch.core.ruz.URL;
import ru.hse.infotouch.core.ruz.api.RuzApiService;
import ru.hse.infotouch.core.ruz.util.JsonParser;
import ru.hse.infotouch.core.ruz.util.LessonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static ru.hse.infotouch.core.ruz.URL.MAX_QUERY_DAYS;

/**
 * @author Evgeny Kholukhoev
 */
@Service
public class RuzApiServiceImpl implements RuzApiService {
    private final JsonParser jsonParser;
    private final LessonParser lessonParser;

    @Value("${ruz.api.url}")
    private String url;

    @Override
    public List<Building> getAllBuildings() {
        return jsonParser.mapStringToList(readRuz(Endpoint.BUILDINGS, null), Building.class);
    }

    @Autowired
    public RuzApiServiceImpl(LessonParser lessonParser) {
        this.lessonParser = lessonParser;
        this.jsonParser = JsonParser.getInstance();
    }

    @Override
    public List<Lecturer> getLecturers(Integer chairId) {
        Map<Param, Object> params = new HashMap<>();
        params.put(Param.CHAIR_ID, chairId);
        String lecturersInString = readRuz(Endpoint.LECTURERS, params);
        return jsonParser.mapStringToList(lecturersInString, Lecturer.class);
    }

    @Override
    public List<Lecturer> getAllLecturers() {
        return jsonParser.mapStringToList(readRuz(Endpoint.LECTURERS, null), Lecturer.class);
    }

    @Override
    public List<Chair> getAllChairs() {
        return jsonParser.mapStringToList(readRuz(Endpoint.CHAIRS, null), Chair.class);
    }

    @Override
    public List<Faculty> getAllFaculties() {
        return jsonParser.mapStringToList(readRuz(Endpoint.FACULTIES, null), Faculty.class);
    }

    @Override
    public List<Lesson> getStudentLessons(Integer studentId, LocalDate fromDate, LocalDate toDate) {
        List<Lesson> result = new LinkedList<>();
        while (ChronoUnit.DAYS.between(fromDate, toDate) > MAX_QUERY_DAYS) {
            result.addAll(getStudentLessonsForShortPeriod(studentId, fromDate, fromDate.plusDays(MAX_QUERY_DAYS)));
            fromDate = fromDate.plusDays(MAX_QUERY_DAYS + 1);
        }
        result.addAll(getStudentLessonsForShortPeriod(studentId, fromDate, toDate));
        return result;
    }

    private List<Lesson> getStudentLessonsForShortPeriod(Integer studentId, LocalDate fromDate, LocalDate toDate) {
        Map<Param, Object> params = new HashMap<>();
        params.put(Param.STUDENT_ID, studentId);
        params.put(Param.FROM_DATE, fromDate.format(formatter));
        params.put(Param.TO_DATE, toDate.format(formatter));
        return getLessons(params);
    }

    @Override
    public List<Lesson> getLecturerLessons(Integer lecturerId, LocalDate fromDate, LocalDate toDate) {
        List<Lesson> result = new LinkedList<>();
        while (ChronoUnit.DAYS.between(fromDate, toDate) > MAX_QUERY_DAYS) {
            result.addAll(getLecturerLessonsForShortPeriod(lecturerId, fromDate, fromDate.plusDays(MAX_QUERY_DAYS)));
            fromDate = fromDate.plusDays(MAX_QUERY_DAYS + 1);
        }
        result.addAll(getLecturerLessonsForShortPeriod(lecturerId, fromDate, toDate));
        return result;
    }

    private List<Lesson> getLecturerLessonsForShortPeriod(Integer lecturerId, LocalDate fromDate, LocalDate toDate) {
        Map<Param, Object> params = new HashMap<>();
        params.put(Param.LECTURER_ID, lecturerId);
        params.put(Param.LESSON_TYPE, URL.LECTURER_LESSON_TYPE);
        params.put(Param.FROM_DATE, fromDate.format(formatter));
        params.put(Param.TO_DATE, toDate.format(formatter));
        return getLessons(params);
    }

    @Override
    public List<Student> getStudents(final Integer groupId) {
        Map<Param, Object> params = new HashMap<>();
        params.put(Param.GROUP_ID, groupId);
        String studentsInString = readRuz(Endpoint.STAFF_OF_GROUP, params);
        return jsonParser.mapStringToList(studentsInString, Student.class);
    }

    @Override
    public List<Group> getGroups() {
        return jsonParser.mapStringToList(readRuz(Endpoint.GROUPS, null), Group.class);
    }

    private List<Lesson> getLessons(Map<Param, ?> params) {
        return lessonParser.parse(jsonParser.mapStringToList(readRuz(Endpoint.LESSONS, params), Lesson.class));
    }

    private String paramsToString(Map<Param, ?> params) {
        StringBuilder parameters = new StringBuilder();
        if (params != null) {
            parameters.append("?");
            for (Param key : params.keySet()) {
                parameters.append(key)
                        .append("=")
                        .append(params.get(key))
                        .append("&");
            }
        }
        return parameters.toString();
    }

    private String readRuz(Endpoint endpoint, Map<Param, ?> params) {
        try {
            java.net.URL url = new java.net.URL(URL.URL.concat(endpoint.toString()).concat(paramsToString(params)));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));

            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
