package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.admin.Ad;

public interface AdRepository extends JpaRepository<Ad, Integer> {
}
