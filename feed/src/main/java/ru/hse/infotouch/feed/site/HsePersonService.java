package ru.hse.infotouch.feed.site;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.hse.infotouch.domain.dto.PersonHseDTO;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HsePersonService {
    private final Logger logger = LoggerFactory.getLogger(HsePersonService.class);

    @Value("${hse.site.persons.request.timeout}")
    private int requestTimeOut;

    private final PersonExtractor personExtractor;
    private final PersonUrlProvider personUrlProvider;

    @Autowired
    public HsePersonService(PersonExtractor personExtractor, PersonUrlProvider personUrlProvider) {
        this.personExtractor = personExtractor;
        this.personUrlProvider = personUrlProvider;
    }


    public List<PersonHseDTO> getAllHsePersons() throws IOException {

        return personUrlProvider.getPersonsUrl()
                .map(this::getPersonsByUrl)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }


    public Stream<PersonUrl> getAllPersonUrlStream() {
        return personUrlProvider.getPersonsUrl();
    }

    public List<PersonHseDTO> getPersonsByUrl(PersonUrl personUrl) {
        String url = personUrl.toString();

        try {
            Document doc = Jsoup.connect(url)
                    .timeout(requestTimeOut)
                    .get();

            List<PersonHseDTO> personsByLetter = personExtractor.extractPersonDTOs(doc, personUrl);

            logger.info("Successfully read persons from {}. Count {}", url, personsByLetter.size());

            return personsByLetter;
        } catch (IOException e) {
            try {
                logger.error("Error parsing Persons for url: {}. Try again...", url);
                Thread.sleep(requestTimeOut * 2);

                return getPersonsByUrl(personUrl);
            } catch (InterruptedException e1) {
                logger.error("Interrupted:", e1);

                return Collections.emptyList();
            }

        }
    }
}
