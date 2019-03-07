package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.Auditorium;

public interface AuditoriumRepository extends JpaRepository<Auditorium, Integer> {
}
