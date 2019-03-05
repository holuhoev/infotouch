package ru.hse.infotouch.feed.site;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.hse.infotouch.domain.Person;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HsePortalService {
    private final Logger logger = LoggerFactory.getLogger(HsePortalService.class);

    // TODO: Вынести в properties
    private static String HSE_GET_PERSON_PATTERN = "https://www.hse.ru/org/persons?ltr=%s;udept=22726";
    private static String HSE_SITE_LINK = "https://www.hse.ru";
    private static int REQUEST_TIMEOUT = 60000;

    private static String[] alphabet = new String[]{
            "А", "Б", "В", "Г", "Д",
            "Е", "Ж", "З", "И", "К",
            "Л", "М", "Н", "О", "П",
            "Р", "С", "Т", "У", "Ф",
            "Х", "Ц", "Ч", "Ш", "Щ",
            "Э", "Ю", "Я",
    };

    public List<Person> getMoscowHsePersons() throws IOException {

        return Arrays.stream(alphabet)
                .map(this::getMoscowHsePersonsByFirstLetter)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Person> getMoscowHsePersonsByFirstLetter(String letter) {
        String url = String.format(HSE_GET_PERSON_PATTERN, letter);

        try {
            Document doc = Jsoup.connect(url)
                    .timeout(REQUEST_TIMEOUT)
                    .get();

            List<Person> personsByLetter = doc.select(".content__inner.content__inner_foot1").stream()
                    .map(e -> {
                        Person person = new Person();

                        person.setFio(e.select(".link.link_dark.large.b").text());
                        person.setLink(HSE_SITE_LINK + e.select(".link.link_dark.large.b").attr("href"));

                        person.setFaculties(e.select(".with-indent7").select(".link").stream()
                                .map(Element::text)
                                .collect(Collectors.toList()));

                        return person;
                    }).collect(Collectors.toList());

            logger.info("Successfully read persons from {}. Count {}", url, personsByLetter.size());

            return personsByLetter;
        } catch (IOException e) {
            try {
                logger.error("Error parsing Persons for url: {}. Try again...", url);
                Thread.sleep(REQUEST_TIMEOUT * 2);

                return getMoscowHsePersonsByFirstLetter(letter);
            } catch (InterruptedException e1) {
                logger.error("Interrupted:", e1);

                return Collections.emptyList();
            }

        }
    }
}
