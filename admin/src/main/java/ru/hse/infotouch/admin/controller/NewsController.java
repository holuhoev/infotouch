package ru.hse.infotouch.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.dto.request.NewsRequest;
import ru.hse.infotouch.domain.models.admin.News;
import ru.hse.infotouch.domain.service.NewsService;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("api/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<News> findOne(@PathVariable("id") int id) {

        return ResponseEntity.ok(newsService.getOneById(id));
    }

    @GetMapping
    public ResponseEntity<List<News>> findAll(@RequestParam(value = "searchString", required = false) String searchString,
                                              @RequestParam(value = "topicId", required = false) Integer topicId,
                                              @RequestParam(value = "tagIds", required = false) int[] tagIds,
                                              @RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.ok(
                newsService.findAll(null,
                        searchString,
                        topicId,
                        tagIds,
                        isNull(page) ? 0 : page
                ));
    }

    @PostMapping
    public ResponseEntity<News> createNews(NewsRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<News> updateNews(@PathVariable("id") int id, NewsRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(newsService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(@PathVariable("id") Integer id) {

        this.newsService.delete(id);
    }
}
