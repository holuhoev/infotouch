package ru.hse.infotouch.device.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.models.admin.Device;
import ru.hse.infotouch.domain.service.DeviceService;

@RestController
@RequestMapping("api/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Device> getOneById(@PathVariable("id") int id) {

        return ResponseEntity.ok(deviceService.getOneById(id));
    }
}

