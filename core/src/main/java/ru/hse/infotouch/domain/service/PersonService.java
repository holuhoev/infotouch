package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.datasource.PersonDatasource;
import ru.hse.infotouch.domain.models.Person;
import ru.hse.infotouch.domain.repo.PersonRepository;

import java.util.*;

@Service
public class PersonService {

    private final PersonRepository repository;
    private final PersonDatasource datasource;

    public PersonService(PersonRepository repository, PersonDatasource datasource) {
        this.repository = repository;
        this.datasource = datasource;
    }

    public void saveAll(List<Person> persons) {
        repository.saveAll(persons);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<Person> findAll(String fio, int page, UUID[] employeesIds) {
        return datasource.findAll(fio, employeesIds, page);
    }

    public Person getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Сотрудника с id \"%s\" не существует", id)));
    }
}
