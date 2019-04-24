package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.models.admin.Event;
import ru.hse.infotouch.domain.models.admin.EventUrl;
import ru.hse.infotouch.domain.models.admin.QEventUrl;
import ru.hse.infotouch.domain.repo.EventUrlRepository;
import ru.hse.infotouch.site.event.SiteEventService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EventService {

    private final EventUrlRepository eventUrlRepository;
    private final QEventUrl qEventUrl = QEventUrl.eventUrl;
    private final SiteEventService siteEventService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public EventService(EventUrlRepository eventUrlRepository, SiteEventService siteEventService) {
        this.eventUrlRepository = eventUrlRepository;
        this.siteEventService = siteEventService;
    }

    public List<Event> findAll(int terminalId) {
        Set<String> urls = getUrls(terminalId);

        return siteEventService.getEventsByUrls(urls);
    }

    public List<Event> findTodayAll(int terminalId) {
        Set<String> urls = getUrls(terminalId);

        LocalDate now = LocalDate.now();

        Set<String> todayUrls = urls.stream()
                .map(url -> url + "/archive/" + now.format(formatter))
                .collect(Collectors.toSet());

        return siteEventService.getEventsByUrls(todayUrls);
    }

    private Set<String> getUrls(int terminalId) {
        return StreamSupport.stream(
                eventUrlRepository.findAll(qEventUrl.terminalId.eq(terminalId)).spliterator(),
                false)
                .map(EventUrl::getUrl)
                .collect(Collectors.toSet());
    }


}
