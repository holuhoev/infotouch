package ru.hse.infotouch.terminal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.models.admin.Announcement;
import ru.hse.infotouch.domain.service.AnnouncementService;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("api/announcement")
public class TerminalAnnouncementController {

    private final AnnouncementService announcementService;

    @Autowired
    public TerminalAnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping
    public ResponseEntity<List<Announcement>> findAll(@RequestParam(value = "searchString", required = false) String searchString,
                                                      @RequestParam(value = "hseLocationId", required = false) Integer hseLocationId,
                                                      @RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.ok(
                announcementService.findAll(
                        searchString,
                        hseLocationId,
                        isNull(page) ? 0 : page
                ));
    }
}
