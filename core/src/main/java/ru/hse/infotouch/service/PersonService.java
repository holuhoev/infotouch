package ru.hse.infotouch.service;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.Person;
import ru.hse.infotouch.domain.QPerson;
import ru.hse.infotouch.repo.PersonRepository;

import java.util.List;
import java.util.Objects;

@Service
public class PersonService {

    private final PersonRepository repository;
    private final QPerson qPerson = QPerson.person;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public void saveAll(List<Person> persons) {
        repository.saveAll(persons);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<Person> findAll(String fio) {
        Objects.requireNonNull(fio);

        String validated = LecturerService.removeRedundantSpace(fio);

        return Lists.newArrayList(repository.findAll(qPerson.fio.containsIgnoreCase(validated)));
    }
    // TODO: methods for searching, like LessonService
    // Джоин с Employee(мб придется немного переделать класс Person)
}
