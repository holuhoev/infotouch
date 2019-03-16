package ru.hse.infotouch.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.datasource.TopicDatasource;
import ru.hse.infotouch.domain.dto.request.TopicRequest;
import ru.hse.infotouch.domain.models.admin.Topic;
import ru.hse.infotouch.domain.repo.TopicRepository;

import java.util.List;

@Service
public class TopicService {

    private final TopicRepository repository;
    private final TopicDatasource datasource;

    @Autowired
    public TopicService(TopicRepository repository, TopicDatasource datasource) {
        this.repository = repository;
        this.datasource = datasource;
    }

    @Transactional
    public Topic create(TopicRequest request) {
        Topic topic = new Topic();

        topic.setTitle(request.getTitle());
        topic.setColor(request.getColor());

        return this.repository.save(topic);
    }

    @Transactional
    public Topic update(int id, TopicRequest request) {
        Topic topic = this.getOneById(id);

        topic.setTitle(request.getTitle());
        topic.setColor(request.getColor());

        return this.repository.save(topic);
    }

    @Transactional
    public void delete(int id) {
        Topic topic = this.getOneById(id);

        this.repository.delete(topic);
    }

    public List<Topic> findAll(String title, String color, int page) {
        return this.datasource.findAll(title, color, page);
    }

    public Topic getOneById(int id) {
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Темы с id \"%d\" не существует", id)));
    }

    public boolean isNotExist(int id) {
        return !repository.existsById(id);
    }
}


