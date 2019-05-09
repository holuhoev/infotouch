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
@RequestMapping("api/terminal")
public class DeviceController {

    private final DeviceService terminalService;

    public DeviceController(DeviceService terminalService) {
        this.terminalService = terminalService;
    }

    @GetMapping
    public ResponseEntity<List<Device>> findAll(@RequestParam(value = "searchString", required = false) String searchString,
                                                @RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.ok(terminalService.findAll(
                searchString,
                isNull(page) ? 0 : page
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getOneById(@PathVariable("id") int id) {

        return ResponseEntity.ok(terminalService.getOneById(id));
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(DeviceRequest request) {

        return ResponseEntity.ok(terminalService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable("id") int id,
                                                 DeviceRequest request) {

        return ResponseEntity.ok(terminalService.update(id, request));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDevice(@PathVariable("id") int id) {

        terminalService.delete(id);
    }
}
