package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.admin.relations.Device2Announcement;
import ru.hse.infotouch.domain.models.admin.relations.Device2AnnouncementId;

public interface Device2AnnouncementRepository extends JpaRepository<Device2Announcement, Device2AnnouncementId> {
}
