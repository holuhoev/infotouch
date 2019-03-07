package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
}
