package ru.hse.infotouch.terminal.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.service.LecturerService;


@RestController
@RequestMapping(value = "/api/lecturer")
public class LecturerController {

    private final LecturerService lecturerService;

    @Autowired
    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }


    @GetMapping
    public ResponseEntity getLecturersByFilter(String searchString) {
        isValidSearchString(searchString);

        return ResponseEntity.ok(lecturerService.searchByString(searchString));
    }


    private void isValidSearchString(String searchString) {
        if (StringUtils.isEmpty(searchString)) {
            throw new IllegalArgumentException("searchString must not be null or empty");
        }
    }
}
