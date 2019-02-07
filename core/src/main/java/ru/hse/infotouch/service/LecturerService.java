package ru.hse.infotouch.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.Lecturer;
import ru.hse.infotouch.domain.QLecturer;
import ru.hse.infotouch.repo.LecturerRepository;

import java.util.List;
import java.util.Objects;

@Service
public class LecturerService {

    private final LecturerRepository repository;
    private final QLecturer qLecturer = QLecturer.lecturer;

    @Autowired
    public LecturerService(LecturerRepository repository) {
        this.repository = repository;
    }


    public List<Lecturer> searchByString(String searchString) {
        Objects.requireNonNull(searchString);

        if (searchString.length() == 0) {
            throw new IllegalArgumentException("searchString must not be empty");
        }

        return Lists.newArrayList(repository.findAll(qLecturer.fio.containsIgnoreCase(searchString)));
    }

    public List<Lecturer> saveAll(Iterable<Lecturer> lecturers) {
        return repository.saveAll(lecturers);
    }
}