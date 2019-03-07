package ru.hse.infotouch.domain.service;

import com.google.common.collect.Lists;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.models.Person;
import ru.hse.infotouch.domain.models.QPerson;
import ru.hse.infotouch.domain.repo.PersonRepository;

import java.util.List;
import java.util.Objects;

import static ru.hse.infotouch.util.Strings.removeRedundantSpace;

@Service
public class PersonService {

    private final int pageSize = 30;
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

    public List<Person> findAll(String fio, int page) {
        Objects.requireNonNull(fio);

        String validated = removeRedundantSpace(fio);

        Pageable pageRequest = PageRequest.of(page, pageSize);

        return Lists.newArrayList(repository.findAll(qPerson.fio.containsIgnoreCase(validated), pageRequest));
    }
}
