package ru.hse.infotouch.domain.datasource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.CollectionExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.hse.infotouch.domain.models.admin.QTag;
import ru.hse.infotouch.domain.models.admin.Tag;
import ru.hse.infotouch.domain.models.admin.Topic;
import ru.hse.infotouch.domain.models.admin.relations.QNews2Tag;
import ru.hse.infotouch.util.Strings;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TagDatasource {
    @Value("${entities.page-size.default}")
    private int pageSize;

    private final QTag qTag = QTag.tag;

    private final EntityManager entityManager;
    private QNews2Tag qNews2Tag = QNews2Tag.news2Tag;

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

    public List<Tag> findByOneNews(int newsId) {
        return findByNews(new int[]{newsId}).getOrDefault(newsId, Collections.emptyList());
    }

    public Map<Integer, List<Tag>> findByNews(int[] newsIds) {
        Objects.requireNonNull(newsIds);

        return new JPAQuery<>(entityManager).select(qNews2Tag.newsId, qTag)
                .from(qTag)
                .leftJoin(qNews2Tag).on(qNews2Tag.tagId.eq(qTag.id))
                .where(qNews2Tag.newsId.in(ArrayUtils.toObject(newsIds)))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(t -> t.get(qNews2Tag.newsId),
                        Collectors.mapping(t -> t.get(qTag), Collectors.toList())));
    }

}
