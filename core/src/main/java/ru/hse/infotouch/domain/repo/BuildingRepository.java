package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.Building;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
}
