package ru.hse.infotouch.feed.site;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.hse.infotouch.domain.Person;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HsePortalService {
    private final Logger logger = LoggerFactory.getLogger(HsePortalService.class);

    @Value("${hse.site.url}")
    private String hseUrl;

    @Value("${hse.site.persons.request.endpoint}")
    private String personsEndpoint;

    @Value("${hse.site.persons.request.timeout}")
    private int requestTimeOut;

    @Value("${hse.site.persons.request.cities}")
    private int[] cities;

    @Value("${hse.site.persons.request.params.letter}")
    private String letterParam;

    @Value("${hse.site.persons.request.params.city}")
    private String cityParam;

    private static String[] alphabet = new String[]{
            "А", "Б", "В", "Г", "Д",
            "Е", "Ж", "З", "И", "К",
            "Л", "М", "Н", "О", "П",
            "Р", "С", "Т", "У", "Ф",
            "Х", "Ц", "Ч", "Ш", "Щ",
            "Э", "Ю", "Я",
    };

    private Stream<String> getCityUrls(int city) {
        return Arrays.stream(alphabet).map(letter -> getUrl(letter, city));
    }

    private String getUrl(String letter, int city) {

        return hseUrl
                + personsEndpoint
                + "?" + letterParam + "=" + letter
                + "&" + cityParam + "=" + city;
    }

    public List<Person> getAllHsePersons() throws IOException {
        List<String> allUrls = Arrays.stream(cities).boxed()
                .flatMap(this::getCityUrls)
                .collect(Collectors.toList());

        return allUrls.stream()
                .map(this::getMoscowHsePersonsByFirstLetter)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }


    private List<Person> getMoscowHsePersonsByFirstLetter(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .timeout(requestTimeOut)
                    .get();

            List<Person> personsByLetter = doc.select(".content__inner.content__inner_foot1").stream()
                    .map(e -> {
                        Person person = new Person();

                        person.setFio(e.select(".link.link_dark.large.b").text());
                        person.setLink(hseUrl + e.select(".link.link_dark.large.b").attr("href"));

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
                Thread.sleep(requestTimeOut * 2);

                return getMoscowHsePersonsByFirstLetter(url);
            } catch (InterruptedException e1) {
                logger.error("Interrupted:", e1);

                return Collections.emptyList();
            }

        }
    }
}
