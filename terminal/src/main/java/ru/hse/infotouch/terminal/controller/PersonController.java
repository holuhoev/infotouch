package ru.hse.infotouch.terminal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.infotouch.domain.models.Person;
import ru.hse.infotouch.domain.service.PersonService;


import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping
    public ResponseEntity<List<Person>> findAll(String fio,
                                                int page) {
        Objects.requireNonNull(fio, "fio must be not null");

        return ResponseEntity.ok(personService.findAll(fio, page));
    }
}
