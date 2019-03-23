package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.admin.relations.Terminal2Ad;
import ru.hse.infotouch.domain.models.admin.relations.Terminal2AdId;

public interface Terminal2AdRepository extends JpaRepository<Terminal2Ad, Terminal2AdId> {
}
