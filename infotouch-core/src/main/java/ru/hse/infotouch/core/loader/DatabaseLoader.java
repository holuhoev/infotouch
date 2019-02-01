package ru.hse.infotouch.core.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.hse.infotouch.core.domain.Lecturer;
import ru.hse.infotouch.core.ruz.api.RuzApiService;

import java.util.List;

// TODO: вынести в отдельный модуль
@Component
public class DatabaseLoader implements CommandLineRunner {
    private final RuzApiService ruzApi;

    @Autowired
    public DatabaseLoader(RuzApiService ruzApi) {
        this.ruzApi = ruzApi;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Lecturer> lecturers = ruzApi.getAllLecturers();
    }
}
