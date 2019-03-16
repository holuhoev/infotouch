package ru.hse.infotouch.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.dto.request.NewsRequest;
import ru.hse.infotouch.domain.models.admin.News;
import ru.hse.infotouch.domain.models.admin.Topic;
import ru.hse.infotouch.domain.repo.NewsRepository;

import java.util.List;

@Service
public class NewsService {

    private final NewsRepository repository;
    private final TopicService topicService;
    private final TagService tagService;

    @Autowired
    public NewsService(NewsRepository repository, TopicService topicService, TagService tagService) {
        this.repository = repository;
        this.topicService = topicService;
        this.tagService = tagService;
    }

    public List<News> findAll() {
        return repository.findAll();
    }

    public News getOneById(int id) {
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Новости с id \"%d\" не существует", id)));
    }

    @Transactional
    public News create(NewsRequest request) {
        requireExistingRelations(request);

        // TODO: set createdBy when Security;

        News news = repository.save(News.createFromRequest(request));

        tagService.insertNewsRelations(news.getId(), request.getTagIds());

        return news;
    }

    @Transactional
    public News update(int id, NewsRequest request) {
        requireExistingRelations(request);

        final News news = this.getOneById(id);

        tagService.deleteAllRelationsByNewsId(id);
        tagService.insertNewsRelations(id, request.getTagIds());
        news.updateFromRequest(request);

        return repository.save(news);
    }

    @Transactional
    public void delete(int id) {
        final News news = this.getOneById(id);

        tagService.deleteAllRelationsByNewsId(id);
        this.repository.delete(news);
    }

    private void requireExistingRelations(NewsRequest newsRequest) {
        // TODO: uiException
        if (tagService.isNotExistsAll(newsRequest.getTagIds())) {
            throw new IllegalArgumentException("Does not all tags exist.");
        }

        if (topicService.isNotExist(newsRequest.getTopicId())) {
            throw new IllegalArgumentException("Topic does not exist.");
        }

    }

}
