package ru.hse.infotouch.domain.datasource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.hse.infotouch.domain.models.admin.*;
import ru.hse.infotouch.domain.models.admin.relations.QNews2Tag;
import ru.hse.infotouch.domain.models.admin.relations.QDevice2News;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class NewsDatasource {

    @Value("${entities.page-size.default}")
    private int pageSize;

    private final EntityManager entityManager;

    private final QNews qNews = QNews.news;
    private final QNews2Tag qNews2Tag = QNews2Tag.news2Tag;
    private final QDevice2News qDevice2News = QDevice2News.device2News;
    private final QTopic qTopic = QTopic.topic;

    private final TagDatasource tagDatasource;

    public NewsDatasource(EntityManager entityManager, TagDatasource tagDatasource) {
        this.entityManager = entityManager;
        this.tagDatasource = tagDatasource;
    }

    public List<News> findAll(Integer terminalId,
                              String searchString,
                              Integer topicId,
                              int[] tagIds,
                              int page) {

        BooleanBuilder whereClause = new BooleanBuilder();

        JPAQuery<Tuple> query = new JPAQuery<>(entityManager).select(qNews, qTopic)
                .from(qNews)
                .leftJoin(qTopic).on(qTopic.id.eq(qNews.topicId));

        if (Objects.nonNull(terminalId)) {
            whereClause.and(qDevice2News.id.terminalId.eq(terminalId));
            query = query.leftJoin(qDevice2News).on(qDevice2News.id.newsId.eq(qNews.id));
        }

        if (StringUtils.isNotEmpty(searchString)) {
            whereClause.and(qNews.title.containsIgnoreCase(searchString)
                    .or(qNews.content.containsIgnoreCase(searchString))
            );
        }

        if (Objects.nonNull(topicId)) {
            whereClause.and(qNews.topicId.eq(topicId));
        }

        if (Objects.nonNull(tagIds) && tagIds.length > 0) {
            whereClause.and(qNews2Tag.tagId.in(ArrayUtils.toObject(tagIds)));
            query = query.leftJoin(qNews2Tag).on(qNews2Tag.newsId.eq(qNews.id));
        }


        List<News> newsList = query.where(whereClause)
                .distinct()
                .offset(pageSize * page)
                .limit(pageSize)
                .fetch()
                .stream()
                .filter(t -> Objects.nonNull(t.get(qNews)))
                .map(tuple -> {
                    News news = tuple.get(qNews);

                    news.setTopic(tuple.get(qTopic));

                    return news;
                }).collect(Collectors.toList());

        int[] newsIds = newsList.stream().map(News::getId).mapToInt(Integer::valueOf).toArray();

        Map<Integer, List<Tag>> newsToTag = tagDatasource.findByNews(newsIds);

        return newsList.stream()
                .peek(news -> news.setTags(newsToTag.get(news.getId())))
                .collect(Collectors.toList());
    }
}
