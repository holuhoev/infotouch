package ru.hse.infotouch.device.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.infotouch.domain.service.ChairService;

@RestController
@RequestMapping("/api/chair")
public class ChairController {

    private final ChairService chairService;


    public ChairController(ChairService chairService) {
        this.chairService = chairService;
    }

    @GetMapping
    public ResponseEntity getAll() {

        return ResponseEntity.ok(chairService.findAll());
    }
}
