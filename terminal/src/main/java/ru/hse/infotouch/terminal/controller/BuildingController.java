package ru.hse.infotouch.terminal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.infotouch.domain.repo.BuildingRepository;


@RestController
@RequestMapping("/api/building")
public class BuildingController {

    private final BuildingRepository buildingRepository;


    public BuildingController(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @GetMapping
    public ResponseEntity getAll() {

        return ResponseEntity.ok(buildingRepository.findAll());
    }
}
