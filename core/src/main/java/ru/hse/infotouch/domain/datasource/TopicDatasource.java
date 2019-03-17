package ru.hse.infotouch.domain.datasource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.models.admin.QTopic;
import ru.hse.infotouch.domain.models.admin.Topic;
import ru.hse.infotouch.util.Strings;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TopicDatasource {

    @Value("${entities.page-size.default}")
    private int pageSize;

    private QTopic qTopic = QTopic.topic;

    private final EntityManager entityManager;

    public TopicDatasource(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Topic> findAll(String title, String color, int page) {
        BooleanBuilder whereClause = new BooleanBuilder();

        if (StringUtils.isNotEmpty(title)) {
            whereClause.and(qTopic.title.containsIgnoreCase(Strings.removeRedundantSpace(title)));
        }

        if (StringUtils.isNotEmpty(color)) {
            whereClause.and(qTopic.color.containsIgnoreCase(Strings.removeRedundantSpace(color)));
        }

        JPAQuery<Topic> query = new JPAQuery<>(entityManager);

        return query.from(qTopic)
                .where(whereClause)
                .offset(pageSize * page)
                .limit(pageSize)
                .fetch();
    }
}
