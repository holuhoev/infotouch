package ru.hse.infotouch.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.dto.request.HseLocationRequest;
import ru.hse.infotouch.domain.models.admin.HseLocation;
import ru.hse.infotouch.domain.service.HseLocationService;

import java.util.List;

@RestController
@RequestMapping("api/hseLocation")
public class HseLocationController {

    private final HseLocationService hseLocationService;

    @Autowired
    public HseLocationController(HseLocationService hseLocationService) {
        this.hseLocationService = hseLocationService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<HseLocation> findOne(@PathVariable("id") int id) {

        return ResponseEntity.ok(hseLocationService.getOneById(id));
    }

    @GetMapping
    public ResponseEntity<List<HseLocation>> findAll() {

        return ResponseEntity.ok(hseLocationService.findAll());
    }

    @PostMapping
    public ResponseEntity<HseLocation> createHseLocation(HseLocationRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hseLocationService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HseLocation> updateHseLocation(@PathVariable("id") int id, HseLocationRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(hseLocationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHseLocation(@PathVariable("id") Integer id) {

        this.hseLocationService.delete(id);
    }
}
