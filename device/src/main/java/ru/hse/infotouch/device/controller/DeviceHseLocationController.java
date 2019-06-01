package ru.hse.infotouch.device.controller;

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
public class DeviceHseLocationController {

    private final HseLocationService hseLocationService;

    @Autowired
    public DeviceHseLocationController(HseLocationService hseLocationService) {
        this.hseLocationService = hseLocationService;
    }


    @GetMapping
    public ResponseEntity<List<HseLocation>> findAll(@RequestParam(value = "buildingId", required = false) Integer buildingId) {

        return ResponseEntity.ok(hseLocationService.findAll(buildingId));
    }
}
