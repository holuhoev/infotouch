package ru.hse.infotouch.feed.site;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.hse.infotouch.domain.models.enums.CityType;

import java.util.Arrays;
import java.util.stream.Stream;

@Component
public class PersonUrlProvider {

    @Value("${hse.site.url}")
    private String hseUrl;

    @Value("${hse.site.persons.request.endpoint}")
    private String personsEndpoint;

    @Value("${hse.site.persons.request.params.letter}")
    private String letterParam;

    @Value("${hse.site.persons.request.params.city}")
    private String cityParam;


    private String[] alphabet = new String[]{
            "А", "Б", "В", "Г", "Д",
            "Е", "Ж", "З", "И", "К",
            "Л", "М", "Н", "О", "П",
            "Р", "С", "Т", "У", "Ф",
            "Х", "Ц", "Ч", "Ш", "Щ",
            "Э", "Ю", "Я"
    };


    private Stream<PersonUrl> getAllUrlsForCity(CityType city) {
        return Arrays.stream(alphabet).map(letter -> getUrl(letter, city));
    }

    private PersonUrl getUrl(String letter, CityType city) {

        return new PersonUrl(hseUrl, personsEndpoint, letterParam, cityParam, letter, city);
    }

    Stream<PersonUrl> getPersonsUrl() {

        return Arrays.stream(CityType.values())
                .filter(city -> !city.equals(CityType.OTHER))
                .flatMap(this::getAllUrlsForCity);
    }
}