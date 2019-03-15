package ru.hse.infotouch.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.models.admin.Topic;
import ru.hse.infotouch.domain.repo.TopicRepository;

@Service
public class TopicService {

    private final TopicRepository repository;

    @Autowired
    public TopicService(TopicRepository repository) {
        this.repository = repository;
    }

    public Topic getOneById(int id){
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Темы с id \"%d\" не существует", id)));
    }
}


