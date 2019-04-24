package ru.hse.infotouch.site.event;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.models.admin.Event;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class SiteEventService {

    public List<Event> getEventsByUrls(Set<String> urls) {

        return urls.stream()
                .map(this::extractFromUrl)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Event> extractFromUrl(String url) {
        return Collections.emptyList();
    }
}
