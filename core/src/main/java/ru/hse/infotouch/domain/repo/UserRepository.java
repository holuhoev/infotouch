package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.hse.infotouch.domain.models.cms.User;

public interface UserRepository extends JpaRepository<User, Integer>, QuerydslPredicateExecutor<User> {
}
