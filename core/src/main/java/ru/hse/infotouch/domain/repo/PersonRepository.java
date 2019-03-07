package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.hse.infotouch.domain.models.Person;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID>, QuerydslPredicateExecutor<Person> {
}

