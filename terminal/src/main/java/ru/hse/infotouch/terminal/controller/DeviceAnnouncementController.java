package ru.hse.infotouch.terminal.controller;

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
    public ResponseEntity<List<Announcement>> findAll(@RequestParam(value = "terminalId") Integer terminalId,
                                                      @RequestParam(value = "searchString", required = false) String searchString,
                                                      @RequestParam(value = "from", required = false) LocalDate from,
                                                      @RequestParam(value = "to", required = false) LocalDate to,
                                                      @RequestParam(value = "page", required = false) Integer page) {

        requireValidDates(from, to);

        return ResponseEntity.ok(
                announcementService.findAll(
                        terminalId,
                        searchString,
                        from,
                        to,
                        isNull(page) ? 0 : page
                ));
    }

    @GetMapping("/today")
    public ResponseEntity<List<Announcement>> findTodayAll(@RequestParam(value = "terminalId") Integer terminalId,
                                                           @RequestParam(value = "searchString", required = false) String searchString,
                                                           @RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.ok(
                announcementService.findAll(
                        terminalId,
                        searchString,
                        LocalDate.now(),
                        LocalDate.now(),
                        isNull(page) ? 0 : page
                ));
    }

    private void requireValidDates(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            return;
        }

        if (from.isAfter(to)) {
            throw new IllegalArgumentException("from must be before or equal to");
        }
    }

}
