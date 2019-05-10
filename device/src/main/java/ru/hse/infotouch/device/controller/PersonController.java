package ru.hse.infotouch.device.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.infotouch.domain.models.Person;
import ru.hse.infotouch.domain.service.PersonService;


import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping
    public ResponseEntity<List<Person>> findAll(@RequestParam(value = "fio", required = false) String fio,
                                                @RequestParam(value = "employeeIds", required = false) UUID[] employeeIds,
                                                @RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.ok(
                personService.findAll(fio,
                        Objects.isNull(page) ? 0 : page,
                        employeeIds)
        );
    }
}
