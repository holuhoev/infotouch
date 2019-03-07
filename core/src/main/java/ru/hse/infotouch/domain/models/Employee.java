package ru.hse.infotouch.domain.models;

import ru.hse.infotouch.domain.dto.EmployeeHseDTO;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "ID")
    private UUID Id;

    @Column(name = "person_id")
    private UUID personId;

    @Column(name = "chair_id")
    private Integer chairId;

    @Column(name = "position")
    private String position;

    @Column(name = "lecturer_id")
    private Integer lecturerId;

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    public Integer getChairId() {
        return chairId;
    }

    public void setChairId(Integer chairId) {
        this.chairId = chairId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Integer lecturerId) {
        this.lecturerId = lecturerId;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }


    public static Employee ofHseDto(EmployeeHseDTO dto) {
        Employee employee = new Employee();

        employee.setId(UUID.randomUUID());
        employee.setChairId(dto.getChairId());
        employee.setLecturerId(dto.getLecturerId());
        employee.setPosition(dto.getPosition());

        return employee;
    }

}
