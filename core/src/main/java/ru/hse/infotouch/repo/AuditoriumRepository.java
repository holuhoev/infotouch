package ru.hse.infotouch.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.Auditorium;

public interface AuditoriumRepository extends JpaRepository<Auditorium, Integer> {
}
