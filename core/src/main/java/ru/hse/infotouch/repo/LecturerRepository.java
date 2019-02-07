package ru.hse.infotouch.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.hse.infotouch.domain.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer>, QuerydslPredicateExecutor<Lecturer> {
}
