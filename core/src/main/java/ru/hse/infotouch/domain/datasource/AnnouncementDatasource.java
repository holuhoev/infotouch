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
    private final QDevice2Announcement qDevice2Announcement = QDevice2Announcement.device2Announcement;

    public AnnouncementDatasource(EntityManager em) {
        this.em = em;
    }

    public List<Announcement> findAll(Integer deviceId,
                                      String searchString,
                                      LocalDate from,
                                      LocalDate to,
                                      int page) {
        BooleanBuilder whereClause = new BooleanBuilder();
        JPAQuery<Announcement> query = new JPAQuery<>(em).select(qAnnouncement)
                .from(qAnnouncement);

        if (deviceId != null) {
            query.leftJoin(qDevice2Announcement).on(qAnnouncement.id.eq(qDevice2Announcement.id.announcementId));
            whereClause.and(qDevice2Announcement.id.deviceId.eq(deviceId));
        }

        if (from != null && to != null) {
            whereClause.and(qAnnouncement.startDate.between(from, to)
                    .or(qAnnouncement.endDate.between(from, to)));
        } else if (from != null) {
            whereClause.and(qAnnouncement.endDate.after(from).or(qAnnouncement.endDate.eq(from)));
        } else if (to != null) {
            whereClause.and(qAnnouncement.startDate.before(to).or(qAnnouncement.startDate.eq(to)));
        }

        if (StringUtils.isNotEmpty(searchString)) {
            whereClause.and(qAnnouncement.title.containsIgnoreCase(searchString)
                    .or(qAnnouncement.content.containsIgnoreCase(searchString))
            );
        }


        return query.where(whereClause)
                .distinct()
                .offset(pageSize * page)
                .limit(pageSize)
                .fetch();
    }

}
