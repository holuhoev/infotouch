package ru.hse.infotouch.device.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.infotouch.domain.dto.BuildingMapDTO;
import ru.hse.infotouch.domain.service.BuildingMapService;

@RestController
@RequestMapping("/api/building/map")
public class BuildingMapController {

    private final BuildingMapService buildingMapService;

    public BuildingMapController(BuildingMapService buildingMapService) {
        this.buildingMapService = buildingMapService;
    }

    @GetMapping("/{buildingId}")
    public ResponseEntity<BuildingMapDTO> getOne(@PathVariable("buildingId") int buildingId) {

        return ResponseEntity.ok(buildingMapService.getOne(buildingId));
    }
}
