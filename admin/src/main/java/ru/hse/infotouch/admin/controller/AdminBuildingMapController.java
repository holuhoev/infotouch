package ru.hse.infotouch.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import ru.hse.infotouch.domain.dto.BuildingMapDTO;
import ru.hse.infotouch.domain.dto.request.CreateEdgesRequest;
import ru.hse.infotouch.domain.dto.request.CreatePointsRequest;
import ru.hse.infotouch.domain.models.map.Edge;
import ru.hse.infotouch.domain.models.map.Point;
import ru.hse.infotouch.domain.service.BuildingMapService;
import ru.hse.infotouch.domain.service.EdgeService;
import ru.hse.infotouch.domain.service.PointService;

import java.util.List;

@RestController
@RequestMapping("/api/building/scheme")
@Api
public class AdminBuildingMapController {
    private final BuildingMapService buildingMapService;
    private final PointService pointService;
    private final EdgeService edgeService;

    public AdminBuildingMapController(BuildingMapService buildingMapService, PointService pointService, EdgeService edgeService) {
        this.buildingMapService = buildingMapService;
        this.pointService = pointService;
        this.edgeService = edgeService;
    }


    @PostMapping("/points/create")
    public ResponseEntity<List<Point>> createNewPoints(@RequestBody CreatePointsRequest request) {
        // todo: выдавать ошибку если не прислали ID комнаты
        // UIException
        return ResponseEntity.ok(pointService.createNew(request.getPoints()));
    }

    @DeleteMapping("/points/{pointId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove point and relations",
            notes = "1. Remove point\n 2.Remove edges 3. Remove from service 4. Remove from scheme element")
    public void deletePoint(@PathVariable("pointId") int pointId) {
        pointService.delete(pointId);
    }

    @PostMapping("/edges")
    public ResponseEntity<List<Edge>> createEdges(@RequestBody CreateEdgesRequest request) {
        return ResponseEntity.ok(edgeService.create(request.getEdges()));
    }

    @GetMapping("/{buildingId}")
    public ResponseEntity<BuildingMapDTO> getOne(@PathVariable("buildingId") int buildingId) {

        return ResponseEntity.ok(buildingMapService.getOne(buildingId));
    }
}
