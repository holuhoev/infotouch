package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.admin.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
