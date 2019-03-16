package ru.hse.infotouch.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.dto.request.TopicRequest;
import ru.hse.infotouch.domain.models.admin.Topic;
import ru.hse.infotouch.domain.service.TopicService;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("api/topic")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<Topic>> findAll(@RequestParam(value = "title", required = false) String title,
                                               @RequestParam(value = "color", required = false) String color,
                                               @RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.ok(topicService.findAll(
                title,
                color,
                isNull(page) ? 0 : page
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> getOneById(@PathVariable("id") int id) {

        return ResponseEntity.ok(topicService.getOneById(id));
    }

    @PostMapping
    public ResponseEntity<Topic> createTopic(TopicRequest request) {

        return ResponseEntity.ok(topicService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable("id") int id,
                                             TopicRequest request) {

        return ResponseEntity.ok(topicService.update(id, request));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable("id") int id) {

        topicService.delete(id);
    }
}
