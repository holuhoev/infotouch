package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.hse.infotouch.domain.models.admin.EventUrl;

public interface EventUrlRepository extends JpaRepository<EventUrl, Integer>, QuerydslPredicateExecutor<EventUrl> {
}
