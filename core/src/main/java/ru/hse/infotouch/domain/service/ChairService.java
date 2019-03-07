package ru.hse.infotouch.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.models.Chair;
import ru.hse.infotouch.domain.models.QChair;
import ru.hse.infotouch.domain.repo.ChairRepository;

import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.apache.commons.lang3.StringUtils.stripToEmpty;

@Service
public class ChairService {

    private final ChairRepository repository;
    private final QChair qChair = QChair.chair;

    @Autowired
    public ChairService(ChairRepository repository) {
        this.repository = repository;
    }

    // TODO:

    public Optional<Chair> findExactChair(String chairName, String facultyName) {

        return StreamSupport.stream(
                repository.findAll(
                        qChair.name.containsIgnoreCase(stripToEmpty(chairName))
                                .and(qChair.facultyName.containsIgnoreCase(stripToEmpty(facultyName)))
                ).spliterator(), false)
                .findFirst();
    }
}
