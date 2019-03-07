package ru.hse.infotouch.domain.models;


import ru.hse.infotouch.domain.models.enums.KindOfWork;
import ru.hse.infotouch.ruz.converter.LocalTimeConverter;
import ru.hse.infotouch.ruz.converter.RuzConvert;
import ru.hse.infotouch.ruz.converter.KindOfWorkConverter;
import ru.hse.infotouch.ruz.util.JsonField;

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

    public Lesson() {
    }


    public void setBeginLesson(LocalTime beginLesson) {
        this.beginLesson = beginLesson;
    }

    public void setEndLesson(LocalTime endLesson) {
        this.endLesson = endLesson;
    }

    public LocalTime getBeginLesson() {
        return beginLesson;
    }

    public LocalTime getEndLesson() {
        return endLesson;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Integer getAuditoriumId() {
        return auditoriumId;
    }

    public void setAuditoriumId(Integer auditoriumId) {
        this.auditoriumId = auditoriumId;
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
                Objects.equals(auditoriumId, lesson.auditoriumId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, beginLesson, endLesson, discipline, kindOfWork, auditoriumId);
    }


}
