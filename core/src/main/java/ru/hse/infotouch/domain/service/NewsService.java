package ru.hse.infotouch.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.hse.infotouch.domain.dto.request.NewsRequest;
import ru.hse.infotouch.domain.models.admin.News;
import ru.hse.infotouch.domain.models.admin.Topic;
import ru.hse.infotouch.domain.repo.NewsRepository;

import java.util.List;

@Service
public class NewsService {

    private final NewsRepository repository;
    private final TopicService topicService;

    @Autowired
    public NewsService(NewsRepository repository, TopicService topicService) {
        this.repository = repository;
        this.topicService = topicService;
    }

    public List<News> findAll() {
        return repository.findAll();
    }

    public News getOneById(int id) {
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Новости с id \"%d\" не существует", id)));
    }

    public News create(NewsRequest request) {
        Topic topic = topicService.getOneById(request.getTopicId());
        News news = News.createFromRequest(request, topic);

        // TODO: set createdBy when Security;

        return repository.save(news);
    }

    public News update(int id, NewsRequest request) {
        Topic topic = topicService.getOneById(request.getTopicId());

        final News news = this.getOneById(id);

        news.updateFromRequest(request, topic);

        return repository.save(news);
    }

    public void delete(int id) {
        final News news = this.getOneById(id);

        this.repository.delete(news);
    }

}
