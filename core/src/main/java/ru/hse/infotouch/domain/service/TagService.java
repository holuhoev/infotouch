package ru.hse.infotouch.domain.service;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.models.admin.News2Tag;
import ru.hse.infotouch.domain.models.admin.QNews2Tag;
import ru.hse.infotouch.domain.repo.News2TagsRepository;
import ru.hse.infotouch.domain.repo.TagRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final News2TagsRepository news2TagsRepository;
    private final EntityManager entityManager;

    private QNews2Tag qNews2Tag = QNews2Tag.news2Tag;

    public TagService(TagRepository tagRepository, News2TagsRepository news2TagsRepository, EntityManager entityManager) {
        this.tagRepository = tagRepository;
        this.news2TagsRepository = news2TagsRepository;
        this.entityManager = entityManager;
    }

    public boolean isNotExistsAll(int[] ids) {
        for (int id : ids) {
            if (!tagRepository.existsById(id)) {
                return true;
            }
        }

        return false;
    }

    public void deleteAllRelationsByNewsId(int newsId) {
        Query query = entityManager.createNativeQuery("delete from news2tag nt where  nt.news_id = :newsId ");
        query.setParameter("newsId", newsId).executeUpdate();
    }

    public void insertNewsRelations(int id, int[] tagIds) {
        List<News2Tag> relations = Arrays.stream(tagIds).boxed()
                .map(tagId -> new News2Tag(id, tagId))
                .collect(Collectors.toList());

        news2TagsRepository.saveAll(relations);
    }
}
