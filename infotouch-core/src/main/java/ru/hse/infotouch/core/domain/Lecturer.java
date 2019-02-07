package ru.hse.infotouch.core.domain;


import ru.hse.infotouch.core.ruz.util.JsonField;

import javax.persistence.*;

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
    @Column(name = "fio", columnDefinition = "nvarchar(255)")
    private String fio;

    @JsonField
    @Column(name = "short_fio", columnDefinition = "nvarchar(255)")
    private String shortFIO;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public Lecturer() { }

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
}
