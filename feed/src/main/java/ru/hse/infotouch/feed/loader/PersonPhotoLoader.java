package ru.hse.infotouch.feed.loader;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.hse.infotouch.domain.models.Person;
import ru.hse.infotouch.domain.repo.PersonRepository;

import java.io.*;

import java.net.URL;
import java.net.URLConnection;

@Component
public class PersonPhotoLoader implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(PersonPhotoLoader.class);
    private final PersonRepository personRepository;

    public PersonPhotoLoader(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        File f = new File("person_photos");
        f.mkdirs();

        personRepository.findAll()
                .stream()
                .filter(person -> StringUtils.isNotEmpty(person.getAvatarUrl()))
                .forEach(this::fetchAndSaveAvatar);

        log.info("FINISHED");
    }

    private void fetchAndSaveAvatar(Person person) {
        try {
            URL url = new URL(person.getAvatarUrl());
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            byte[] buffer = new byte[in.available()];
            in.read(buffer);

            File targetFile = new File("person_photos/" + person.getId().toString() + ".jpg");
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
        } catch (Exception e) {
            log.error("Could not save photo for: {}. \n Error: {}", person.getFio(), e.getMessage());
            return;
        }
    }
}
