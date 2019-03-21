package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.admin.HseLocation;

public interface HseLocationRepository extends JpaRepository<HseLocation, Integer> {
}
