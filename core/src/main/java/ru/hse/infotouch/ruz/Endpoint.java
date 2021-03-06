package ru.hse.infotouch.ruz;

public enum Endpoint {
    GROUPS("groups"),
    LESSONS("personLessons"),
    LECTURERS("lecturers"),
    FACULTIES("faculties"),
    STAFF_OF_GROUP("StaffOfGroup"),
    CHAIRS("chairs"),
    BUILDINGS("buildings"),
    AUDITORIUMS("auditoriums");

    private final String value;

    Endpoint(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
