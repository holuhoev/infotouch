package ru.hse.infotouch.feed.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.hse.infotouch.core.domain.Lecturer;
import ru.hse.infotouch.core.repo.LecturerRepository;
import ru.hse.infotouch.core.ruz.api.RuzApiService;

import java.util.List;

@Component
public class LecturerLoader implements CommandLineRunner {
    private final RuzApiService ruzApi;

    private final LecturerRepository repository;
    private final Logger logger = LoggerFactory.getLogger(LecturerLoader.class);

    @Autowired
    public LecturerLoader(RuzApiService ruzApi, LecturerRepository repository) {
        this.ruzApi = ruzApi;
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Lecturer loader starts");

        long t1 = System.currentTimeMillis();

        List<Lecturer> allLecturers = ruzApi.getAllLecturers();
        long t1_end = System.currentTimeMillis();
        logger.info("Get all lecturers from RUZ took: {} ms", t1_end - t1);

        repository.saveAll(allLecturers);
        long end = System.currentTimeMillis();
        logger.info("Save all lecturers took: {} ms", end - t1_end);
    }
}
