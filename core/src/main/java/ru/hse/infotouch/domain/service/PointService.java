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
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PointService {
    private final PointRepository pointRepository;
    private final EntityManager entityManager;
    private final QPoint qPoint = QPoint.point;

    public PointService(PointRepository pointRepository, EntityManager entityManager) {
        this.pointRepository = pointRepository;
        this.entityManager = entityManager;
    }

    public List<Point> createNew(int buildingSchemeId, List<CreatePointDTO> createPointDTOS) {
        List<Point> toSave = createPointDTOS.stream()
                .map(toCreate -> Point.createFromRequest(toCreate, buildingSchemeId))
                .collect(Collectors.toList());

        return pointRepository.saveAll(toSave);
    }


    @Transactional
    public List<Point> saveAll(int buildingSchemeId, List<CreatePointDTO> createPointDTOS) {
        removePointsAndRelations(buildingSchemeId);

        // 3. Создать новые точки
        List<Point> toSave = createPointDTOS.stream()
                .map(toCreate -> Point.createFromRequest(toCreate, buildingSchemeId))
                .collect(Collectors.toList());

        List<Point> saved = pointRepository.saveAll(toSave);


        // 4. Проставить связи c scheme_element
        saved.stream()
                .filter(point -> Objects.nonNull(point.getSchemeElementId()))
                .map(point -> {
                    Query query = entityManager.createNativeQuery("update map_element set point_id=:pointId where id=:schemeElementId");
                    query.setParameter("pointId", point.getId())
                            .setParameter("schemeElementId", point.getSchemeElementId());
                    return query;
                }).forEach(Query::executeUpdate);

        return saved;
    }


    private void removePointsAndRelations(int buildingSchemeId) {
        // 1. Взять ID всех старых точек
        List<Integer> ids = getPointIdsByScheme(buildingSchemeId);

        if (ids.size() > 0) {
            // 2. Удалить все связи старых точек в scheme_element
            Query query = entityManager.createNativeQuery("update map_element set point_id=null where point_id in (:point_id_list)");
            query.setParameter("point_id_list", ids).executeUpdate();

            // 3. Удалить связи в ребрах
            Query deleteEdges = entityManager.createNativeQuery("delete from edge where left_point_id in (:point_id_list)");
            deleteEdges.setParameter("point_id_list", ids).executeUpdate();

            // 4. Удалить сами точки
            Query delete = entityManager.createNativeQuery("delete from point where building_scheme_id=:schemeId");
            delete.setParameter("schemeId", buildingSchemeId).executeUpdate();
        }
    }

    private List<Integer> getPointIdsByScheme(int buildingSchemeId) {
        return new JPAQuery<Integer>(entityManager)
                .select(qPoint.id)
                .from(qPoint)
                .where(qPoint.buildingSchemeId.eq(buildingSchemeId))
                .fetch();
    }
}
