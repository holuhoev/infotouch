package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.hse.infotouch.domain.models.map.Edge;

public interface EdgeRepository extends JpaRepository<Edge, Integer>, QuerydslPredicateExecutor<Edge> {
}
