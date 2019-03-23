package ru.hse.infotouch.domain.datasource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import ru.hse.infotouch.domain.models.admin.Announcement;
import ru.hse.infotouch.domain.models.admin.QAnnouncement;
import ru.hse.infotouch.domain.models.admin.QHseLocation;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class AnnouncementDatasource {

    @Value("${entities.page-size.default}")
    private int pageSize;

    private final EntityManager em;

    private final QHseLocation qHseLocation = QHseLocation.hseLocation;
    private final QAnnouncement qAnnouncement = QAnnouncement.announcement;

    public AnnouncementDatasource(EntityManager em) {
        this.em = em;
    }

    public List<Announcement> findAll(String searchString,
                                      Integer hseLocationId,
                                      int page) {
        BooleanBuilder whereClause = new BooleanBuilder();
        JPAQuery<Tuple> query = new JPAQuery<>(em).select(qAnnouncement, qHseLocation)
                .from(qAnnouncement)
                .leftJoin(qHseLocation).on(qHseLocation.id.eq(qAnnouncement.hseLocationId));

        if (StringUtils.isNotEmpty(searchString)) {
            whereClause.and(qAnnouncement.title.containsIgnoreCase(searchString)
                    .or(qAnnouncement.content.containsIgnoreCase(searchString))
            );
        }

        if (Objects.nonNull(hseLocationId)) {
            whereClause.and(qAnnouncement.hseLocationId.eq(hseLocationId));
        }

        return query.where(whereClause)
                .distinct()
                .offset(pageSize * page)
                .limit(pageSize)
                .fetch()
                .stream()
                .filter(t -> Objects.nonNull(t.get(qAnnouncement)))
                .map(tuple -> {
                    Announcement announcement = tuple.get(qAnnouncement);

                    announcement.setHseLocation(tuple.get(qHseLocation));

                    return announcement;
                }).collect(Collectors.toList());
    }

}
