package ru.hse.infotouch.domain.datasource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import ru.hse.infotouch.domain.models.admin.QTag;
import ru.hse.infotouch.domain.models.admin.Tag;
import ru.hse.infotouch.domain.models.admin.Topic;
import ru.hse.infotouch.util.Strings;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TagDatasource {
    private final int pageSize = 30;

    private final EntityManager entityManager;

    private QTag qTag = QTag.tag;

    public TagDatasource(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Tag> findAll(String title, int page) {
        BooleanBuilder whereClause = new BooleanBuilder();

        if (StringUtils.isNotEmpty(title)) {
            whereClause.and(qTag.title.containsIgnoreCase(Strings.removeRedundantSpace(title)));
        }

        JPAQuery<Tag> query = new JPAQuery<>(entityManager);

        return query.from(qTag)
                .where(whereClause)
                .offset(pageSize * page)
                .limit(pageSize)
                .fetch();
    }

}
