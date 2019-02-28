package ru.hse.infotouch.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.Building;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
}
