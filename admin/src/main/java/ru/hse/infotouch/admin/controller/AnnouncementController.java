package ru.hse.infotouch.admin.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.dto.request.AnnouncementRequest;
import ru.hse.infotouch.domain.models.admin.Announcement;
import ru.hse.infotouch.domain.service.AnnouncementService;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("api/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Announcement> findOne(@PathVariable("id") int id) {

        return ResponseEntity.ok(announcementService.getOneById(id));
    }

    @GetMapping
    public ResponseEntity<List<Announcement>> findAll(@RequestParam(value = "deviceId", required = false) Integer deviceId,
                                                      @RequestParam(value = "searchString", required = false) String searchString,
                                                      @RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.ok(
                announcementService.findAll(
                        deviceId,
                        searchString,
                        isNull(page) ? 0 : page
                ));
    }

    @PostMapping
    public ResponseEntity<Announcement> createAnnouncement(@RequestBody @Valid AnnouncementRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(announcementService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Announcement> updateAnnouncement(@PathVariable("id") int id,
                                                           @RequestBody @Valid AnnouncementRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(announcementService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnnouncement(@PathVariable("id") Integer id) {

        this.announcementService.delete(id);
    }
}
