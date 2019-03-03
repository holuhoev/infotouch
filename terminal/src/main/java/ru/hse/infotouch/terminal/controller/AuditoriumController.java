package ru.hse.infotouch.terminal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.infotouch.domain.Auditorium;
import ru.hse.infotouch.service.AuditoriumService;

import java.util.List;

@RestController
@RequestMapping("/auditorium")
public class AuditoriumController {

    private final AuditoriumService auditoriumService;

    public AuditoriumController(AuditoriumService auditoriumService) {
        this.auditoriumService = auditoriumService;
    }

    @GetMapping
    public ResponseEntity<List<Auditorium>> findAll() {
        return ResponseEntity.ok(auditoriumService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auditorium> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(auditoriumService.getOneById(id));
    }
}
