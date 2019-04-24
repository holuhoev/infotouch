package ru.hse.infotouch.terminal.controller;

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
public class TerminalEventController {

    private final EventService eventService;

    public TerminalEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{terminalId}")
    public ResponseEntity<List<Event>> findAllByTerminal(@PathVariable("terminalId") int terminalId) {
        // cache with redis(key=terminal_Id + current_hour) with ttl;
        return ResponseEntity.ok(eventService.findAll(terminalId));
    }
}
