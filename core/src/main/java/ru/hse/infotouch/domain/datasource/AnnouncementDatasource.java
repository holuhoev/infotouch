package ru.hse.infotouch.domain.datasource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import ru.hse.infotouch.domain.models.admin.Announcement;
import ru.hse.infotouch.domain.models.admin.QAnnouncement;
import ru.hse.infotouch.domain.models.admin.relations.QDevice2Announcement;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Repository
public class AnnouncementDatasource {

    @Value("${entities.page-size.default}")
    private int pageSize;

    private final EntityManager em;

    private final QAnnouncement qAnnouncement = QAnnouncement.announcement;

    public AnnouncementDatasource(EntityManager em) {
        this.em = em;
    }

    public List<Announcement> findAll(Integer deviceId,
                                      String searchString,
                                      int page) {
        BooleanBuilder whereClause = new BooleanBuilder();
        JPAQuery<Announcement> query = new JPAQuery<>(em).select(qAnnouncement)
                .from(qAnnouncement);

        if (deviceId != null) {
            whereClause.and(qAnnouncement.deviceId.eq(deviceId));
        }

        if (StringUtils.isNotEmpty(searchString)) {
            whereClause.and(qAnnouncement.title.containsIgnoreCase(searchString));
        }

        return query.where(whereClause)
                .distinct()
                .offset(pageSize * page)
                .limit(pageSize)
                .orderBy(qAnnouncement.id.asc())
                .fetch();
    }

}
