package ru.hse.infotouch.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.dto.request.TagRequest;
import ru.hse.infotouch.domain.models.admin.Tag;
import ru.hse.infotouch.domain.service.TagService;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("api/tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> findAll(@RequestParam(value = "title", required = false) String title,
                                               @RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.ok(tagService.findAll(
                title,
                isNull(page) ? 0 : page
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getOneById(@PathVariable("id") int id) {

        return ResponseEntity.ok(tagService.getOneById(id));
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(TagRequest request) {

        return ResponseEntity.ok(tagService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable("id") int id,
                                             TagRequest request) {

        return ResponseEntity.ok(tagService.update(id, request));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable("id") int id) {

        tagService.delete(id);
    }
}
