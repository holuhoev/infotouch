package ru.hse.infotouch.domain.service;

import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.dto.request.EventUrlRequest;
import ru.hse.infotouch.domain.models.admin.EventUrl;
import ru.hse.infotouch.domain.models.admin.QEventUrl;
import ru.hse.infotouch.domain.repo.EventUrlRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EventUrlService {
    private final EventUrlRepository repository;
    private final QEventUrl qEventUrl = QEventUrl.eventUrl;

    public EventUrlService(EventUrlRepository repository) {
        this.repository = repository;
    }

    public List<EventUrl> findAll(Integer deviceId) {

        BooleanBuilder where = new BooleanBuilder();

        if (deviceId != null) {
            where.and(qEventUrl.deviceId.eq(deviceId));
        }

        return StreamSupport.stream(
                repository.findAll(where).spliterator(),
                false)
                .collect(Collectors.toList());
    }


    public Set<String> getUrls(int deviceId) {

        return findAll(deviceId).stream()
                .map(EventUrl::getUrl)
                .collect(Collectors.toSet());
    }

    public EventUrl create(EventUrlRequest request) {
        EventUrl eventUrl = new EventUrl();

        eventUrl.setDeviceId(request.getDeviceId());
        eventUrl.setUrl(request.getUrl());

        return repository.save(eventUrl);
    }

    public EventUrl update(int id, EventUrlRequest request) {
        EventUrl eventUrl = this.getOneById(id);

        eventUrl.setDeviceId(request.getDeviceId());
        eventUrl.setUrl(request.getUrl());

        return repository.save(eventUrl);
    }

    public void delete(int id) {
        EventUrl eventUrl = this.getOneById(id);

        repository.delete(eventUrl);
    }

    public EventUrl getOneById(int id) {
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Ссылки мероприятия с id \"%d\" не существует", id)));
    }

}
