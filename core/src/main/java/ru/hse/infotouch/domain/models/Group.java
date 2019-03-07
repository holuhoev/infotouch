package ru.hse.infotouch.domain.models;


import ru.hse.infotouch.domain.models.enums.Course;
import ru.hse.infotouch.domain.models.enums.EducationType;
import ru.hse.infotouch.ruz.converter.CourseConverter;
import ru.hse.infotouch.ruz.converter.EducationTypeConverter;
import ru.hse.infotouch.ruz.converter.RuzConvert;
import ru.hse.infotouch.ruz.util.JsonField;

import javax.persistence.*;
import java.util.Objects;

/**
 * Группа студентов.
 *
 * @author Evgeny Kholukhoev
 */
@Entity
@Table(name = "STUDGROUP")
public class Group extends RuzObject {
    @Id
    @JsonField(name = "groupOid")
    @Column(name = "ID")
    private Integer Id;

    @JsonField
    @Column(name = "course")
    @RuzConvert(converter = CourseConverter.class)
    private Course course;

    @Column(name = "institute_id")
    private Integer instituteId;

    @JsonField(name = "facultyOid")
    @Column(name = "faculty_id")
    private Integer facultyId;

    @JsonField
    @Column(name = "group_number")
    private String number;

    @Column(name = "education_type")
    @JsonField(name = "kindEducation")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(Id, group.Id) &&
                course == group.course &&
                Objects.equals(instituteId, group.instituteId) &&
                Objects.equals(facultyId, group.facultyId) &&
                Objects.equals(number, group.number) &&
                educationType == group.educationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, course, instituteId, facultyId, number, educationType);
    }
}

