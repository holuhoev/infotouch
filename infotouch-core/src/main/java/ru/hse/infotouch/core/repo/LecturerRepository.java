package ru.hse.infotouch.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.hse.infotouch.core.domain.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer>, QuerydslPredicateExecutor<Lecturer> {
}
