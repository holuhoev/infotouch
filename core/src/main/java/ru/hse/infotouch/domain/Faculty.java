package ru.hse.infotouch.domain;


import ru.hse.infotouch.ruz.util.JsonField;

import javax.persistence.*;

/**
 * Образовательная программа.
 *
 * @author Evgeny Kholukhoev
 */
@Entity
@Table(name = "FACULTY")
public class Faculty {

    @Id
    @JsonField(name = "facultyOid")
    @Column(name = "ID")
    private Integer Id;

    @Column(name = "institute_id")
    private Integer instituteId;

    @JsonField
    @Column(name = "faculty_name")
    private String name;

    @Transient
    @JsonField
    private String institute;

    public Faculty() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public Integer getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Integer instituteId) {
        this.instituteId = instituteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }
}
