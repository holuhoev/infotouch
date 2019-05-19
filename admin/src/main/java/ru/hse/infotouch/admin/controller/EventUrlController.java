package ru.hse.infotouch.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.dto.request.EventUrlRequest;
import ru.hse.infotouch.domain.models.admin.EventUrl;
import ru.hse.infotouch.domain.service.EventUrlService;

import java.util.List;


@RestController
@RequestMapping("api/eventUrl")
public class EventUrlController {

    private final EventUrlService eventUrlService;

    @Autowired
    public EventUrlController(EventUrlService eventUrlService) {
        this.eventUrlService = eventUrlService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EventUrl> findOne(@PathVariable("id") int id) {

        return ResponseEntity.ok(eventUrlService.getOneById(id));
    }

    @GetMapping
    public ResponseEntity<List<EventUrl>> findAll(@RequestParam(value = "deviceId", required = false) Integer deviceId) {

        return ResponseEntity.ok(eventUrlService.findAll(deviceId));
    }

    @PostMapping
    public ResponseEntity<EventUrl> createEventUrl(@RequestBody EventUrlRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventUrlService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventUrl> updateEventUrl(@PathVariable("id") int id, @RequestBody EventUrlRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(eventUrlService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEventUrl(@PathVariable("id") Integer id) {

        this.eventUrlService.delete(id);
    }

}
