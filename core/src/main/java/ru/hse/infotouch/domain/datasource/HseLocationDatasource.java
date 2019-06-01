package ru.hse.infotouch.domain.datasource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.hse.infotouch.domain.models.admin.HseLocation;
import ru.hse.infotouch.domain.models.admin.QHseLocation;
import ru.hse.infotouch.domain.models.map.BuildingScheme;
import ru.hse.infotouch.domain.models.map.QBuildingScheme;
import ru.hse.infotouch.domain.models.map.QPoint;
import ru.hse.infotouch.domain.models.map.QSchemeElement;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class HseLocationDatasource {

    private final EntityManager entityManager;
    private final QHseLocation qHseLocation = QHseLocation.hseLocation;
    private final QBuildingScheme qBuildingScheme = QBuildingScheme.buildingScheme;
    private final QPoint qPoint = QPoint.point;
    private final QSchemeElement qSchemeElement = QSchemeElement.schemeElement;

    @Autowired
    public HseLocationDatasource(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<HseLocation> findAll(Integer buildingId) {

        BooleanBuilder where = new BooleanBuilder();

        if (buildingId != null) {
            where.and(qHseLocation.buildingId.eq(buildingId));
        }

        return new JPAQuery<>(entityManager)
                .select(qHseLocation, qBuildingScheme)
                .from(qHseLocation)
                .leftJoin(qPoint).on(qPoint.id.eq(qHseLocation.pointId))
                .leftJoin(qSchemeElement).on(qPoint.schemeElementId.eq(qSchemeElement.id))
                .leftJoin(qBuildingScheme).on(qSchemeElement.buildingSchemeId.eq(qBuildingScheme.id))
                .where(where)
                .distinct()
                .fetch()
                .stream()
                .filter(t -> Objects.nonNull(t.get(qHseLocation)))
                .map(tuple -> {
                    HseLocation location = tuple.get(qHseLocation);

                    BuildingScheme buildingScheme = tuple.get(qBuildingScheme);

                    if (buildingScheme != null) {
                        location.setBuildingSchemeId(buildingScheme.getId());
                        location.setFloor(buildingScheme.getFloor());
                    }

                    return location;
                }).collect(Collectors.toList());
    }
}
