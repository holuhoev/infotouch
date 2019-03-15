package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.admin.Topic;

public interface TopicRepository extends JpaRepository<Topic,Integer> {
}
