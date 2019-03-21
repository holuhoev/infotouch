package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.admin.relations.Terminal2News;
import ru.hse.infotouch.domain.models.admin.relations.Terminal2NewsId;

public interface Terminal2NewsRepository extends JpaRepository<Terminal2News, Terminal2NewsId> {
}
