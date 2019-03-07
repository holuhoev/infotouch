package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.hse.infotouch.domain.models.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer>, QuerydslPredicateExecutor<Lecturer> {
}
