package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.hse.infotouch.domain.models.map.SchemeElement;

public interface SchemeElementRepository extends JpaRepository<SchemeElement, Integer>, QuerydslPredicateExecutor<SchemeElement> {
}
