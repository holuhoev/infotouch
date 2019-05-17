package ru.hse.infotouch.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.dto.request.DeviceRequest;
import ru.hse.infotouch.domain.models.admin.Device;
import ru.hse.infotouch.domain.service.DeviceService;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("api/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public ResponseEntity<List<Device>> findAll(@RequestParam(value = "searchString", required = false) String searchString,
                                                @RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.ok(deviceService.findAll(
                searchString,
                isNull(page) ? 0 : page
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getOneById(@PathVariable("id") int id) {

        return ResponseEntity.ok(deviceService.getOneById(id));
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody DeviceRequest request) {

        return ResponseEntity.ok(deviceService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable("id") int id,
                                               @RequestBody DeviceRequest request) {

        return ResponseEntity.ok(deviceService.update(id, request));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDevice(@PathVariable("id") int id) {

        deviceService.delete(id);
    }
}
