package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.admin.News;

public interface NewsRepository extends JpaRepository<News, Integer> {
}
