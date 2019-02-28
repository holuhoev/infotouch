package ru.hse.infotouch.domain;


import ru.hse.infotouch.domain.enums.KindOfWork;
import ru.hse.infotouch.ruz.converter.LocalTimeConverter;
import ru.hse.infotouch.ruz.converter.RuzConvert;
import ru.hse.infotouch.ruz.converter.KindOfWorkConverter;
import ru.hse.infotouch.ruz.util.JsonField;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * @author Evgeny Kholukhoev
 */
public class Lesson extends RuzObject {
    @JsonField
    private String date;

    @JsonField
    @RuzConvert(converter = LocalTimeConverter.class)
    private LocalTime beginLesson;

    @JsonField
    @RuzConvert(converter = LocalTimeConverter.class)
    private LocalTime endLesson;

    @JsonField
    private String discipline;

    @JsonField(name = "auditoriumOid")
    private Integer auditoriumId;

    @JsonField
    @RuzConvert(converter = KindOfWorkConverter.class)
    private KindOfWork kindOfWork;

    private Integer hours;


    public Lesson() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public KindOfWork getKindOfWork() {
        return kindOfWork;
    }

    public void setKindOfWork(KindOfWork kindOfWork) {
        this.kindOfWork = kindOfWork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(date, lesson.date) &&
                Objects.equals(beginLesson, lesson.beginLesson) &&
                Objects.equals(endLesson, lesson.endLesson) &&
                Objects.equals(discipline, lesson.discipline) &&
                kindOfWork == lesson.kindOfWork &&
                Objects.equals(hours, lesson.hours) &&
                Objects.equals(auditoriumId, lesson.auditoriumId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, beginLesson, endLesson, discipline, kindOfWork, hours, auditoriumId);
    }

    public Integer getAuditoriumId() {
        return auditoriumId;
    }

    public void setAuditoriumId(Integer auditoriumId) {
        this.auditoriumId = auditoriumId;
    }
}
