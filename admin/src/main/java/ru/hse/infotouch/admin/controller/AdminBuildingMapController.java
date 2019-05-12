package ru.hse.infotouch.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.dto.BuildingMapDTO;
import ru.hse.infotouch.domain.dto.request.CreateEdgesRequest;
import ru.hse.infotouch.domain.dto.request.CreatePointsRequest;
import ru.hse.infotouch.domain.models.map.Edge;
import ru.hse.infotouch.domain.models.map.Point;
import ru.hse.infotouch.domain.service.BuildingMapService;
import ru.hse.infotouch.domain.service.PointService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/building/scheme")
public class AdminBuildingMapController {
    private final BuildingMapService buildingMapService;
    private final PointService pointService;

    public AdminBuildingMapController(BuildingMapService buildingMapService, PointService pointService) {
        this.buildingMapService = buildingMapService;
        this.pointService = pointService;
    }

    // 1. Save new points and remove all edges
    @PostMapping("/{schemeId}/points")
    public ResponseEntity<List<Point>> createPoints(@PathVariable("schemeId") int schemeId,
                                                    CreatePointsRequest request) {
        return ResponseEntity.ok(pointService.saveAll(schemeId, request.getPoints()));
    }

    // 2. Create edges
    @PostMapping("/edges")
    public ResponseEntity<List<Edge>> createEdges(CreateEdgesRequest request) {
        return ResponseEntity.ok(Collections.emptyList());
    }

    // 3. Get BuildingMapDTO
    @GetMapping("/{buildingId}")
    public ResponseEntity<BuildingMapDTO> getOne(@PathVariable("buildingId") int buildingId) {

        return ResponseEntity.ok(buildingMapService.getOne(buildingId));
    }
    // сохранение scheme element
    // сохранение hse location object id
    // 4. создание схемы и карты пока не предусмотрено
}
