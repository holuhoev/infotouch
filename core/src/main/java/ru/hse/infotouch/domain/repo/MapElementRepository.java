package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.hse.infotouch.domain.models.map.MapElement;

public interface MapElementRepository extends JpaRepository<MapElement, Integer>, QuerydslPredicateExecutor<MapElement> {
}
