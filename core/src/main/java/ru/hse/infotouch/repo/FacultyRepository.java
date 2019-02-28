package ru.hse.infotouch.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
}
