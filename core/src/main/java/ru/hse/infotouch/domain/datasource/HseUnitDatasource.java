package ru.hse.infotouch.domain.datasource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import ru.hse.infotouch.domain.models.admin.HseUnit;
import ru.hse.infotouch.domain.models.admin.QHseUnit;
import ru.hse.infotouch.domain.models.map.BuildingScheme;
import ru.hse.infotouch.domain.models.map.QBuildingScheme;
import ru.hse.infotouch.domain.models.map.QSchemeElement;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class HseUnitDatasource {

    private final EntityManager entityManager;
    private final QHseUnit qHseUnit = QHseUnit.hseUnit;
    private final QSchemeElement qSchemeElement = QSchemeElement.schemeElement;
    private final QBuildingScheme qBuildingScheme = QBuildingScheme.buildingScheme;

    public HseUnitDatasource(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<HseUnit> findAll(Integer buildingId) {
        BooleanBuilder where = new BooleanBuilder();

        if (buildingId != null) {
            where.and(qHseUnit.buildingId.eq(buildingId));
        }

        return new JPAQuery<>(entityManager)
                .select(qHseUnit, qBuildingScheme)
                .from(qHseUnit)
                .leftJoin(qSchemeElement).on(qSchemeElement.id.eq(qHseUnit.schemeElementId))
                .leftJoin(qBuildingScheme).on(qSchemeElement.buildingSchemeId.eq(qBuildingScheme.id))
                .where(where)
                .distinct()
                .fetch()
                .stream()
                .filter(t -> Objects.nonNull(t.get(qHseUnit)))
                .map(tuple -> {
                    HseUnit unit = tuple.get(qHseUnit);

                    BuildingScheme buildingScheme = tuple.get(qBuildingScheme);

                    if (buildingScheme != null) {
                        unit.setBuildingSchemeId(buildingScheme.getId());
                        unit.setFloor(buildingScheme.getFloor());
                    }

                    return unit;
                }).collect(Collectors.toList());
    }
}
