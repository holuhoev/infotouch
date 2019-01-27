package ru.hse.infotouch.core.domain;



import ru.hse.infotouch.core.ruz.util.JsonAttribute;

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
    @JsonAttribute(name = "chairOid")
    @Column(name = "ID")
    private Integer Id;

    @JsonAttribute(name = "facultyOid")
    @Column(name = "faculty_id")
    private Integer facultyId;

    @JsonAttribute
    @Column(name = "chair_name")
    private String name;

    @JsonAttribute
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
