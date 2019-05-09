package ru.hse.infotouch.domain.datasource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Repository;
import ru.hse.infotouch.domain.models.map.Point;
import ru.hse.infotouch.domain.models.map.QPoint;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PointDatasource {

    private final EntityManager entityManager;
    private final QPoint qPoint = QPoint.point;

    public PointDatasource(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Point> findAll(int[] roomIds) {

        JPAQuery<Point> query = new JPAQuery<>(entityManager)
                .select(qPoint)
                .from(qPoint)
                .where(qPoint.roomId.in(ArrayUtils.toObject(roomIds)));

        return query.fetch();
    }
}
