package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.admin.relations.Device2News;
import ru.hse.infotouch.domain.models.admin.relations.Device2NewsId;

public interface Device2NewsRepository extends JpaRepository<Device2News, Device2NewsId> {
}
