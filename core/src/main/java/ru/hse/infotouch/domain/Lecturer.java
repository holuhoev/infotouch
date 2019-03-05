package ru.hse.infotouch.domain;


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

    @Column(name = "link")
    private String link;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
