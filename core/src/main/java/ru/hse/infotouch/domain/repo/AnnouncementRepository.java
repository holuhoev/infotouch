package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.admin.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
}
