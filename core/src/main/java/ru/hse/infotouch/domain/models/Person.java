package ru.hse.infotouch.domain.models;

import ru.hse.infotouch.domain.dto.PersonHseDTO;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "fio")
    private String fio;

    @Column(name = "url")
    private String url;

    @Column(name = "emails")
    private String emails;

    @Transient
    private List<Employee> employees;

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public static Person ofHseDto(PersonHseDTO dto) {
        Person person = new Person();

        UUID personID = UUID.randomUUID();
        person.setId(personID);
        person.setFio(dto.getFio());
        person.setEmails(String.join(",", dto.getEmails()));
        person.setUrl(dto.getUrl());

        person.setEmployees(dto.getEmployees().stream()
                .map(Employee::ofHseDto)
                .peek(employee -> employee.setPersonId(personID))
                .collect(Collectors.toList())
        );

        return person;
    }
}
