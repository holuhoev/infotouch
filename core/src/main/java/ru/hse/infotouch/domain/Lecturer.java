package ru.hse.infotouch.domain;


import ru.hse.infotouch.domain.enums.CityType;
import ru.hse.infotouch.ruz.util.JsonField;

import javax.persistence.*;
import java.util.Objects;

/**
 * Преподаватель.
 *
 * @author Evgeny Kholukhoev
 */
@Entity
@Table(name = "lecturer")
public class Lecturer extends RuzObject {
    @Id
    @JsonField(name = "lecturerOid")
    @Column(name = "ID")
    private Integer Id;

    @JsonField(name = "chairOid")
    @Column(name = "chair_id")
    private Integer chairId;

    @JsonField
    @Column(name = "fio")
    private String fio;

    @JsonField
    @Column(name = "short_fio")
    private String shortFIO;

    @Column(name = "url")
    private String url;

    @Column(name = "chair_name")
    private String chairName;

    @Column(name = "faculty_name")
    private String facultyName;

    @Column(name = "lecturer_city")
    private CityType chairCity;

    public String getChairName() {
        return chairName;
    }

    public void setChairName(String chairName) {
        this.chairName = chairName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public CityType getChairCity() {
        return chairCity;
    }

    public void setChairCity(CityType chairCity) {
        this.chairCity = chairCity;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public Lecturer() {
    }

    public Integer getChairId() {
        return chairId;
    }

    public void setChairId(Integer chairId) {
        this.chairId = chairId;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getShortFIO() {
        return shortFIO;
    }

    public void setShortFIO(String shortFio) {
        this.shortFIO = shortFio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecturer lecturer = (Lecturer) o;
        return Objects.equals(Id, lecturer.Id) &&
                Objects.equals(chairId, lecturer.chairId) &&
                Objects.equals(fio, lecturer.fio) &&
                Objects.equals(shortFIO, lecturer.shortFIO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, chairId, fio, shortFIO);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
