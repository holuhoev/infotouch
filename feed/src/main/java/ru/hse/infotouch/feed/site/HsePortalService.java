package ru.hse.infotouch.feed.site;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.Person;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HsePortalService {
    public static String HSE_GET_PERSON_PATTERN = "https://www.hse.ru/org/persons?ltr=%D0%90;udept=22726";
    public static String HSE_SITE_LINK = "https://www.hse.ru";

    public List<Person> getAllHsePersons() throws IOException {
        Document doc = Jsoup.connect(HSE_GET_PERSON_PATTERN).get();

        return doc.select(".content__inner.content__inner_foot1").stream()
                .map(e -> {
                    Person person = new Person();

                    person.setFio(e.select(".link.link_dark.large.b").text());
                    person.setLink(HSE_SITE_LINK + e.select(".link.link_dark.large.b").attr("href"));

                    person.setFaculties(e.select(".with-indent7").select(".link").stream()
                            .map(Element::text)
                            .collect(Collectors.toList()));

                    return person;
                }).collect(Collectors.toList());
    }
}
