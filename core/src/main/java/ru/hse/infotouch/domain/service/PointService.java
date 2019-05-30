package ru.hse.infotouch.domain.service;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.dto.request.CreatePointDTO;
import ru.hse.infotouch.domain.models.map.Point;
import ru.hse.infotouch.domain.models.map.QPoint;
import ru.hse.infotouch.domain.repo.PointRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointService {
    private final PointRepository pointRepository;
    private final EntityManager entityManager;

    public PointService(PointRepository pointRepository, EntityManager entityManager) {
        this.pointRepository = pointRepository;
        this.entityManager = entityManager;
    }

    public Point getOneById(int id) {

        return this.pointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Точки с id \"%d\" не существует", id)));
    }

    public List<Point> createNew(List<CreatePointDTO> createPointDTOS) {
        List<Point> toSave = createPointDTOS.stream()
                .map(Point::createFromRequest)
                .collect(Collectors.toList());

        return pointRepository.saveAll(toSave);
    }

    @Transactional
    public void delete(int pointId) {
        Query deleteEdges = entityManager.createNativeQuery("delete from edge where left_point_id = :pointId or right_point_id = :pointId");
        deleteEdges.setParameter("pointId", pointId).executeUpdate();

        Query removeFromHseLocations = entityManager.createNativeQuery("update hse_location set point_id = null where point_id = :pointId");
        removeFromHseLocations.setParameter("pointId", pointId).executeUpdate();

        pointRepository.deleteById(pointId);
    }
}
