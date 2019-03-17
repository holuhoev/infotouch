package ru.hse.infotouch.domain.datasource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.hse.infotouch.domain.models.admin.QTag;
import ru.hse.infotouch.domain.models.admin.Tag;
import ru.hse.infotouch.domain.models.admin.Topic;
import ru.hse.infotouch.util.Strings;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TagDatasource {
    @Value("${entities.page-size.default}")
    private int pageSize;

    private final QTag qTag = QTag.tag;

    private final EntityManager entityManager;

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
