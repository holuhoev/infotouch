package ru.hse.infotouch.domain;



import ru.hse.infotouch.ruz.util.JsonField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Департамент
 *
 * @author Evgeny Kholukhoev
 */
@Entity
@Table(name = "CHAIR")
public class Chair extends RuzObject {
    @javax.persistence.Id
    @JsonField(name = "chairOid")
    @Column(name = "ID")
    private Integer Id;

    @JsonField(name = "facultyOid")
    @Column(name = "faculty_id")
    private Integer facultyId;

    @JsonField
    @Column(name = "chair_name")
    private String name;

    @JsonField
    @Column(name = "code")
    private String code;

    public Chair() {
    }

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
