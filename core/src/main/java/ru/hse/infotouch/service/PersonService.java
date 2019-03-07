package ru.hse.infotouch.service;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.Person;
import ru.hse.infotouch.repo.PersonRepository;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public void saveAll(List<Person> persons) {
        repository.saveAll(persons);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    // TODO: methods for searching, like LessonService
    // Джоин с Employee(мб придется немного переделать класс Person)
}
