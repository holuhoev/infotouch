package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.admin.Terminal;

public interface TerminalRepository extends JpaRepository<Terminal, Integer> {
}
