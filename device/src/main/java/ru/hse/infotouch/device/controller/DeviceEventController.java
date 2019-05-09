package ru.hse.infotouch.device.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.infotouch.domain.models.admin.Event;
import ru.hse.infotouch.domain.service.EventService;

import java.util.List;

@RestController
@RequestMapping("api/event")
public class DeviceEventController {

    private final EventService eventService;

    public DeviceEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<List<Event>> findAllByDevice(@PathVariable("deviceId") int deviceId) {
        // cache with redis(key=device_Id + current_hour) with ttl;
        return ResponseEntity.ok(eventService.findAll(deviceId));
    }

    @GetMapping("/today/{deviceId}")
    public ResponseEntity<List<Event>> findTodayAllByDevice(@PathVariable("deviceId") int deviceId) {
        // cache with redis(key=device_Id + current_hour) with ttl;
        return ResponseEntity.ok(eventService.findTodayAll(deviceId));
    }
}
