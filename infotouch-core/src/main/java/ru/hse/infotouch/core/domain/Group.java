package ru.hse.infotouch.core.domain;


import ru.hse.infotouch.core.ruz.converter.CourseConverter;
import ru.hse.infotouch.core.ruz.converter.EducationTypeConverter;
import ru.hse.infotouch.core.ruz.converter.RuzConvert;
import ru.hse.infotouch.core.ruz.util.JsonAttribute;

import javax.persistence.*;

/**
 * Группа студентов.
 *
 * @author Evgeny Kholukhoev
 */
@Entity
@Table(name = "STUDGROUP")
public class Group extends RuzObject {
    @Id
    @JsonAttribute(name = "groupOid")
    @Column(name = "ID")
    private Integer Id;

    @JsonAttribute
    @Column(name = "course")
    @RuzConvert(converter = CourseConverter.class)
    private Course course;

    @Column(name = "institute_id")
    private Integer instituteId;

    @JsonAttribute(name = "facultyOid")
    @Column(name = "faculty_id")
    private Integer facultyId;

    @JsonAttribute
    @Column(name = "group_number")
    private String number;

    @Column(name = "education_type")
    @JsonAttribute(name = "kindEducation")
    @RuzConvert(converter = EducationTypeConverter.class)
    private EducationType educationType;

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public Group() {

    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Integer instituteId) {
        this.instituteId = instituteId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public EducationType getEducationType() {
        return educationType;
    }

    public void setEducationType(EducationType educationType) {
        this.educationType = educationType;
    }
}

