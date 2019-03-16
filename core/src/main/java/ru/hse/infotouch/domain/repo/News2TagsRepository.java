package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.hse.infotouch.domain.models.admin.News2Tag;

public interface News2TagsRepository extends JpaRepository<News2Tag, Integer>, QuerydslPredicateExecutor<News2Tag> {

}
