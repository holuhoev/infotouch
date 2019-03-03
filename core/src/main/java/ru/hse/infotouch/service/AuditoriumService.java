package ru.hse.infotouch.service;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.Auditorium;
import ru.hse.infotouch.repo.AuditoriumRepository;

import java.util.List;

@Service
public class AuditoriumService {

    private final AuditoriumRepository repository;

    public AuditoriumService(AuditoriumRepository repository) {
        this.repository = repository;
    }

    public List<Auditorium> findAll() {
        return this.repository.findAll();
    }

    public Auditorium getOneById(Integer id) {
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Аудитории с id \"%d\" не существует", id)));
    }
}
