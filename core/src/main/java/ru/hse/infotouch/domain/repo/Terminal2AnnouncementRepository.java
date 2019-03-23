package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.admin.relations.Terminal2Announcement;
import ru.hse.infotouch.domain.models.admin.relations.Terminal2AnnouncementId;

public interface Terminal2AnnouncementRepository extends JpaRepository<Terminal2Announcement, Terminal2AnnouncementId> {
}
