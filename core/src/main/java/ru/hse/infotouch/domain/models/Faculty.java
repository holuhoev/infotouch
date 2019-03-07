package ru.hse.infotouch.domain.models;


import ru.hse.infotouch.ruz.util.JsonField;

import javax.persistence.*;
import java.util.Objects;

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

    @JsonField
    @Column(name = "faculty_name")
    private String name;

    public Faculty() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(Id, faculty.Id) &&
                Objects.equals(name, faculty.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name);
    }
}
