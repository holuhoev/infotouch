package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.map.Point;

public interface PointRepository extends JpaRepository<Point, Integer> {
}
