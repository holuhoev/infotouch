package ru.hse.infotouch.service;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.Chair;
import ru.hse.infotouch.domain.Lecturer;
import ru.hse.infotouch.domain.QChair;
import ru.hse.infotouch.domain.QLecturer;
import ru.hse.infotouch.repo.ChairRepository;
import ru.hse.infotouch.repo.LecturerRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.function.Function.identity;

@Service
public class LecturerService {

    private final LecturerRepository repository;
    private final ChairRepository chairRepository;
    private final QLecturer qLecturer = QLecturer.lecturer;
    private final QChair qChair = QChair.chair;

    private static String removeRedundantSpace(String searchString) {
        return Arrays.stream(searchString.split(" "))
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(" "));
    }

    @Autowired
    public LecturerService(LecturerRepository repository, ChairRepository chairRepository) {
        this.repository = repository;
        this.chairRepository = chairRepository;
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

    public Optional<Lecturer> findByFioAndChairs(String fio, List<String> chairNames) {
        List<Lecturer> lecturerList = this.findAllBy(fio);

        // 1 вариант
        Integer[] chairs = chairNames.stream()
                .map(chair -> chairRepository.findAll(qChair.name.containsIgnoreCase(chair)))
                .flatMap(iterable -> StreamSupport.stream(iterable.spliterator(), false))
                .map(Chair::getId)
                .toArray(Integer[]::new);


        repository.findAll(qLecturer.fio.containsIgnoreCase(fio)
                .and(qLecturer.chairId.in(chairs)));

        // 2 вариант
        Map<String, Lecturer> chairToId = lecturerList.stream()
                .filter(this::lecturerHasChair)
                .collect(Collectors.toMap(lecturer -> {
                    Optional<Chair> chair = chairRepository.findById(lecturer.getChairId());

                    return chair.get().getName();
                }, identity()));

        return chairNames.stream()
                .map(chairToId::get)
                .filter(Objects::nonNull)
                .findFirst();
    }


    private boolean lecturerHasChair(Lecturer lecturer) {
        return chairRepository.findById(lecturer.getChairId()).isPresent();
    }
}