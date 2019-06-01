package ru.hse.infotouch.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.dto.request.HseLocationPointsRequest;
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
    public ResponseEntity<List<HseLocation>> findAll(@RequestParam(value = "buildingId", required = false) Integer buildingId) {

        return ResponseEntity.ok(hseLocationService.findAll(buildingId));
    }

    @PostMapping
    public ResponseEntity<HseLocation> createHseLocation(@RequestBody HseLocationRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hseLocationService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HseLocation> updateHseLocation(@PathVariable("id") int id, @RequestBody HseLocationRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(hseLocationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHseLocation(@PathVariable("id") Integer id) {

        this.hseLocationService.delete(id);
    }

    @PutMapping("/point")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setPoints(@RequestBody HseLocationPointsRequest request) {
        this.hseLocationService.savePoints(request);
    }

    @DeleteMapping("/point/{pointId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePoint(@PathVariable("pointId") int pointId) {
        this.hseLocationService.removeLocationsFromPoint(pointId);
    }
}
