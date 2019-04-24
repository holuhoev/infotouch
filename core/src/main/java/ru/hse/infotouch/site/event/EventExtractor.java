package ru.hse.infotouch.site.event;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.models.admin.Event;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventExtractor {

    public List<Event> extractEvents(Document doc) {

        return doc.select(".b-events__item.js-events-item")
                .stream()
                .map(this::extractEvent)
                .collect(Collectors.toList());
    }

    private Event extractEvent(Element eventElement) {
        Event event = new Event();

        event.setTitle(extractTitle(eventElement));
        event.setAddInfo(extractAddInfo(eventElement));
        event.setUrl(extractUrl(eventElement));
        event.setTopics(extractTopics(eventElement));
        event.setTags(extractTags(eventElement));
        event.setTime(extractTime(eventElement));

        return event;
    }

    private String extractTitle(Element event) {

        return event.select(".b-events__body")
                .select(".b-events__body_title.large")
                .select("a")
                .text();
    }

    private String extractUrl(Element event) {

        return event.select(".b-events__body")
                .select(".b-events__body_title.large")
                .select("a")
                .attr("href");
    }

    private String extractAddInfo(Element event) {

        return event.select(".b-events__status")
                .select(".grey.small")
                .text();
    }

    private Set<String> extractTopics(Element event) {

        return event.select(".b-events__body")
                .select(".tag-set.smaller")
                .select(".rubric")
                .select("span").stream()
                .map(Element::text)
                .collect(Collectors.toSet());
    }

    private Set<String> extractTags(Element event) {

        return event.select(".b-events__body")
                .select(".tag-set.smaller")
                .select(".tag").stream()
                .map(Element::text)
                .collect(Collectors.toSet());
    }

    private String extractTime(Element event) {

        return event.select(".b-events__extra.date")
                .text();
    }
}
