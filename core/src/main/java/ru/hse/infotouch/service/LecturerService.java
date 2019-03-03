package ru.hse.infotouch.service;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.Lecturer;
import ru.hse.infotouch.domain.QLecturer;
import ru.hse.infotouch.repo.LecturerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LecturerService {

    private final LecturerRepository repository;
    private final QLecturer qLecturer = QLecturer.lecturer;

    private static String removeRedundantSpace(String searchString) {
        return Arrays.stream(searchString.split(" "))
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(" "));
    }

    @Autowired
    public LecturerService(LecturerRepository repository) {
        this.repository = repository;
    }


    public List<Lecturer> findAllBy(String searchString) {
        Objects.requireNonNull(searchString);

        if (searchString.length() == 0) {
            throw new IllegalArgumentException("searchString must not be empty");
        }

        String validatedSearchString = removeRedundantSpace(searchString);

        return Lists.newArrayList(repository.findAll(qLecturer.fio.containsIgnoreCase(validatedSearchString)));
    }


    public List<Lecturer> saveAll(Iterable<Lecturer> lecturers) {
        return repository.saveAll(lecturers);
    }

    public boolean isLecturerExists(int lecturerId) {
        return repository.existsById(lecturerId);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}