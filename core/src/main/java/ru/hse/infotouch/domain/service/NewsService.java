package ru.hse.infotouch.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.datasource.NewsDatasource;
import ru.hse.infotouch.domain.datasource.TagDatasource;
import ru.hse.infotouch.domain.dto.request.NewsRequest;
import ru.hse.infotouch.domain.models.admin.News;
import ru.hse.infotouch.domain.repo.NewsRepository;

import java.util.List;

@Service
public class NewsService {

    private final NewsRepository repository;
    private final TopicService topicService;
    private final DeviceService deviceService;
    private final TagService tagService;

    private final NewsDatasource newsDatasource;
    private final TagDatasource tagDatasource;

    @Autowired
    public NewsService(NewsRepository repository,
                       TopicService topicService,
                       DeviceService deviceService,
                       TagService tagService,
                       NewsDatasource newsDatasource,
                       TagDatasource tagDatasource) {
        this.repository = repository;
        this.topicService = topicService;
        this.deviceService = deviceService;
        this.tagService = tagService;
        this.newsDatasource = newsDatasource;
        this.tagDatasource = tagDatasource;
    }

    public List<News> findAll(Integer deviceId,
                              String searchString,
                              Integer topicId,
                              int[] tagIds,
                              int page) {

        return newsDatasource.findAll(deviceId, searchString, topicId, tagIds, page);
    }

    public News getOneById(int id) {
        News news = this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Новости с id \"%d\" не существует", id)));

        news.setTopic(topicService.getOneById(news.getTopicId()));
        news.setTags(tagDatasource.findByOneNews(id));

        return news;
    }

    @Transactional
    public News create(NewsRequest request) {
        requireExistingRelations(request);

        // TODO: set createdBy when Security;

        News news = repository.save(News.createFromRequest(request));

        deviceService.insertNewsRelations(news.getId(), request.getDeviceIds());
        tagService.insertNewsRelations(news.getId(), request.getTagIds());

        return news;
    }

    @Transactional
    public News update(int id, NewsRequest request) {
        requireExistingRelations(request);

        final News news = this.getOneById(id);

        tagService.deleteAllRelationsByNewsId(id);
        tagService.insertNewsRelations(id, request.getTagIds());

        deviceService.deleteAllNewsRelations(id);
        deviceService.insertNewsRelations(id, request.getDeviceIds());

        news.updateFromRequest(request);

        News saved = repository.save(news);

        saved.setTopic(topicService.getOneById(news.getTopicId()));
        saved.setTags(tagDatasource.findByOneNews(id));

        return saved;
    }

    @Transactional
    public void delete(int id) {
        final News news = this.getOneById(id);

        tagService.deleteAllRelationsByNewsId(id);
        deviceService.deleteAllNewsRelations(id);

        this.repository.delete(news);
    }

    private void requireExistingRelations(NewsRequest newsRequest) {
        // TODO: uiException
        if (topicService.isNotExist(newsRequest.getTopicId())) {
            throw new IllegalArgumentException("Topic does not exist.");
        }

        if (tagService.isNotExistsAll(newsRequest.getTagIds())) {
            throw new IllegalArgumentException("Does not all tags exist.");
        }

        if (deviceService.isNotExistAll(newsRequest.getDeviceIds())) {
            throw new IllegalArgumentException("Does not all devices exist.");
        }

    }

}
