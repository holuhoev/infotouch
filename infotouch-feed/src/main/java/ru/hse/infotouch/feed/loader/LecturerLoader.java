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

    //    private final LecturerRepository repository;
//    private final Logger logger = LoggerFactory.getLogger(LecturerLoader.class);
//
    @Autowired
    public LecturerLoader(RuzApiService ruzApi) {
        this.ruzApi = ruzApi;
    }

    @Override
    public void run(String... args) throws Exception {
        Lecturer lecturer = new Lecturer();
//        logger.info("Lecturer loader starts");
//
//        long t = System.currentTimeMillis();
//        try {
//            List<Lecturer> allLecturers = ruzApi.getAllLecturers();
//            repository.saveAll(allLecturers);
//        } finally {
//            long end = System.currentTimeMillis();
//            logger.info("Lecturer loader took: {} ms", end - t);
//        }
    }
}
