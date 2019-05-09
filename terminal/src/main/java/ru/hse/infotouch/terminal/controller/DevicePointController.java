package ru.hse.infotouch.terminal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.hse.infotouch.domain.datasource.PointDatasource;
import ru.hse.infotouch.domain.models.map.Point;

import java.util.List;

@RestController
@RequestMapping("/api/point")
public class DevicePointController {

    private final PointDatasource pointDatasource;

    public DevicePointController(PointDatasource pointDatasource) {
        this.pointDatasource = pointDatasource;
    }

    @GetMapping
    public ResponseEntity<List<Point>> findAll(@RequestParam(value = "roomIds", required = false) int[] roomIds) {
        return ResponseEntity.ok(pointDatasource.findAll(roomIds));
    }
}
