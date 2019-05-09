package ru.hse.infotouch.device.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.models.Room;
import ru.hse.infotouch.domain.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<Room>> findAll(@RequestParam(value = "buildingId", required = false) Integer buildingId) {
        return ResponseEntity.ok(roomService.findAll(buildingId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(roomService.getOneById(id));
    }
}
