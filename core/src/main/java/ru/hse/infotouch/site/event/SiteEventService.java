package ru.hse.infotouch.site.event;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.models.admin.Event;


import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class SiteEventService {
    private final Logger log = LoggerFactory.getLogger(SiteEventService.class);
    private final EventExtractor eventExtractor;

    @Autowired
    public SiteEventService(EventExtractor eventExtractor) {
        this.eventExtractor = eventExtractor;
    }

    public List<Event> getEventsByUrls(Set<String> urls) {

        return urls.stream()
                .map(this::getEventsByUrl)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Event> getEventsByUrl(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .timeout(4000)
                    .get();

            return eventExtractor.extractEvents(doc);
        } catch (IOException e) {
            log.error("get event page with url {}. Error: {}", url, e);
        }

        return Collections.emptyList();
    }
}
