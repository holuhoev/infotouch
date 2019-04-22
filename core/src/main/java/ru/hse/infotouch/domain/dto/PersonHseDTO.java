package ru.hse.infotouch.domain.dto;

import ru.hse.infotouch.domain.models.enums.CityType;

import java.util.List;

public class PersonHseDTO {
    private String fio;
    private String url;
    private String[] emails;
    private CityType city;
    private String avatarUrl;

    private List<EmployeeHseDTO> employees;

    public PersonHseDTO() {
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

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

    public String[] getEmails() {
        return emails;
    }

    public void setEmails(String[] emails) {
        this.emails = emails;
    }

    public List<EmployeeHseDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeHseDTO> employees) {
        this.employees = employees;
    }

    public CityType getCity() {
        return city;
    }

    public void setCity(CityType city) {
        this.city = city;
    }
}
