package ru.hse.infotouch.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.models.admin.HseUnit;
import ru.hse.infotouch.domain.service.HseUnitService;

import java.util.List;


@RestController
@RequestMapping("api/hseUnit")
public class DeviceHseUnitController {

    private final HseUnitService hseUnitService;

    @Autowired
    public DeviceHseUnitController(HseUnitService hseUnitService) {
        this.hseUnitService = hseUnitService;
    }


    @GetMapping
    public ResponseEntity<List<HseUnit>> findAll(@RequestParam(value = "buildingId", required = false) Integer buildingId) {

        return ResponseEntity.ok(hseUnitService.findAll(buildingId));
    }

}
