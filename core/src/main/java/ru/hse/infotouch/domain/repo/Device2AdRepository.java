package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.admin.relations.Device2Ad;
import ru.hse.infotouch.domain.models.admin.relations.Device2AdId;

public interface Device2AdRepository extends JpaRepository<Device2Ad, Device2AdId> {
}
