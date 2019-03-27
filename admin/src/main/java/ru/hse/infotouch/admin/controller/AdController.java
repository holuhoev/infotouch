package ru.hse.infotouch.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.dto.request.AdRequest;
import ru.hse.infotouch.domain.models.admin.Ad;
import ru.hse.infotouch.domain.service.AdService;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("api/ad")
public class AdController {

    private final AdService adService;

    @Autowired
    public AdController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Ad> findOne(@PathVariable("id") int id) {

        return ResponseEntity.ok(adService.getOneById(id));
    }

    @GetMapping
    public ResponseEntity<List<Ad>> findAll(@RequestParam(value = "searchString", required = false) String searchString,
                                            @RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.ok(
                adService.findAll(
                        searchString,
                        isNull(page) ? 0 : page
                ));
    }

    @PostMapping
    public ResponseEntity<Ad> createAd(AdRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ad> updateAd(@PathVariable("id") int id, AdRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(adService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAd(@PathVariable("id") Integer id) {

        this.adService.delete(id);
    }
}
