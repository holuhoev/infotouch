package ru.hse.infotouch.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.models.admin.Announcement;
import ru.hse.infotouch.domain.service.AnnouncementService;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("api/announcement")
public class DeviceAnnouncementController {

    private final AnnouncementService announcementService;

    @Autowired
    public DeviceAnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping
    public ResponseEntity<List<Announcement>> findAll(@RequestParam(value = "deviceId") Integer deviceId,
                                                      @RequestParam(value = "searchString", required = false) String searchString,
                                                      @RequestParam(value = "page", required = false) Integer page) {


        return ResponseEntity.ok(
                announcementService.findAll(
                        deviceId,
                        searchString,
                        isNull(page) ? 0 : page
                ));
    }

    @GetMapping("/today")
    public ResponseEntity<List<Announcement>> findTodayAll(@RequestParam(value = "deviceId") Integer deviceId,
                                                           @RequestParam(value = "searchString", required = false) String searchString,
                                                           @RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.ok(
                announcementService.findAll(
                        deviceId,
                        searchString,
                        isNull(page) ? 0 : page
                ));
    }
}
