package ru.hse.infotouch.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.hse.infotouch.domain.Chair;

public interface ChairRepository extends JpaRepository<Chair, Integer>, QuerydslPredicateExecutor<Chair> {
}
