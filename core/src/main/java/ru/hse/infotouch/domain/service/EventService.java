package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.models.admin.Event;
import ru.hse.infotouch.domain.models.admin.EventUrl;
import ru.hse.infotouch.domain.models.admin.QEventUrl;
import ru.hse.infotouch.domain.repo.EventUrlRepository;
import ru.hse.infotouch.site.event.SiteEventService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EventService {

    private final EventUrlRepository eventUrlRepository;
    private final QEventUrl qEventUrl = QEventUrl.eventUrl;
    private final SiteEventService siteEventService;

    public EventService(EventUrlRepository eventUrlRepository, SiteEventService siteEventService) {
        this.eventUrlRepository = eventUrlRepository;
        this.siteEventService = siteEventService;
    }

    public List<Event> findAll(int terminalId) {
        Set<String> urls = StreamSupport.stream(
                eventUrlRepository.findAll(qEventUrl.terminalId.eq(terminalId)).spliterator(),
                false)
                .map(EventUrl::getUrl)
                .collect(Collectors.toSet());

        return siteEventService.getEventsByUrls(urls);
    }

}
