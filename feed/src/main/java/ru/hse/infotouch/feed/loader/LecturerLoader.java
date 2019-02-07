package ru.hse.infotouch.feed.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.hse.infotouch.domain.Lecturer;
import ru.hse.infotouch.ruz.api.RuzApiService;
import ru.hse.infotouch.service.LecturerService;

import java.util.List;

@Component
public class LecturerLoader implements CommandLineRunner {
    private final RuzApiService ruzApi;

    private final LecturerService service;
    private final Logger logger = LoggerFactory.getLogger(LecturerLoader.class);

    @Autowired
    public LecturerLoader(RuzApiService ruzApi, LecturerService service) {
        this.ruzApi = ruzApi;
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Lecturer loader starts");

        long t1 = System.currentTimeMillis();

        List<Lecturer> allLecturers = ruzApi.getAllLecturers();
        long t1_end = System.currentTimeMillis();
        logger.info("Get all lecturers from RUZ took: {} ms", t1_end - t1);

        service.saveAll(allLecturers);
        long end = System.currentTimeMillis();
        logger.info("Save all lecturers took: {} ms", end - t1_end);
    }
}
