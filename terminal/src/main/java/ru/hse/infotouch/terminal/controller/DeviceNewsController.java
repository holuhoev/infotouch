package ru.hse.infotouch.terminal.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.infotouch.domain.models.admin.News;
import ru.hse.infotouch.domain.service.NewsService;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("api/news")
public class DeviceNewsController {

    private final NewsService newsService;

    @Autowired
    public DeviceNewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<List<News>> findAll(@RequestParam(value = "terminalId") Integer terminalId,
                                              @RequestParam(value = "searchString", required = false) String searchString,
                                              @RequestParam(value = "topicId", required = false) Integer topicId,
                                              @RequestParam(value = "tagIds", required = false) int[] tagIds,
                                              @RequestParam(value = "page", required = false) Integer page) {
        // 1. проверяем, может ли юзер смотреть данный терминал user2device

        return ResponseEntity.ok(
                newsService.findAll(terminalId,
                        searchString,
                        topicId,
                        tagIds,
                        isNull(page) ? 0 : page
                ));
    }
}
