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

    private final EventUrlService eventUrlService;
    private final SiteEventService siteEventService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public EventService(EventUrlService eventUrlService, SiteEventService siteEventService) {
        this.eventUrlService = eventUrlService;
        this.siteEventService = siteEventService;
    }

    public List<Event> findAll(int deviceId) {
        Set<String> urls = eventUrlService.getUrls(deviceId);

        return siteEventService.getEventsByUrls(urls);
    }

    public List<Event> findTodayAll(int deviceId) {
        Set<String> urls = eventUrlService.getUrls(deviceId);

        LocalDate now = LocalDate.now();

        Set<String> todayUrls = urls.stream()
                .map(url -> url + "/archive/" + now.format(formatter))
                .collect(Collectors.toSet());

        return siteEventService.getEventsByUrls(todayUrls);
    }


}
