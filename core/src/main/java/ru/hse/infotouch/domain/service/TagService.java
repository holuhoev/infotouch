package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.datasource.TagDatasource;
import ru.hse.infotouch.domain.dto.request.TagRequest;
import ru.hse.infotouch.domain.models.admin.relations.QNews2Tag;
import ru.hse.infotouch.domain.models.admin.relations.News2Tag;

import ru.hse.infotouch.domain.models.admin.Tag;
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
    private final TagDatasource tagDatasource;

    private final EntityManager entityManager;

    private QNews2Tag qNews2Tag = QNews2Tag.news2Tag;

    public TagService(TagRepository tagRepository, News2TagsRepository news2TagsRepository, TagDatasource tagDatasource, EntityManager entityManager) {
        this.tagRepository = tagRepository;
        this.news2TagsRepository = news2TagsRepository;
        this.tagDatasource = tagDatasource;
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

    public List<Tag> findAll(String title, int page) {
        return tagDatasource.findAll(title, page);
    }

    public Tag getOneById(int id) {
        return this.tagRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Тэга с id \"%d\" не существует", id)));
    }

    @Transactional
    public Tag create(TagRequest request) {
        Tag tag = new Tag();

        tag.setTitle(request.getTitle());

        return tagRepository.save(tag);
    }

    @Transactional
    public Tag update(int id, TagRequest request) {
        Tag tag = this.getOneById(id);

        tag.setTitle(request.getTitle());

        return tagRepository.save(tag);
    }

    @Transactional
    public void delete(int id) {
        Tag tag = this.getOneById(id);

        tagRepository.delete(tag);
    }
}
