package ru.hse.infotouch.domain.models.admin.relations;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "news2tag")
public class News2Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "news_id")
    private Integer newsId;

    @Column(name = "tag_id")
    private Integer tagId;

    public News2Tag(Integer newsId, Integer tagId) {
        this.newsId = newsId;
        this.tagId = tagId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News2Tag news2Tag = (News2Tag) o;
        return Objects.equals(id, news2Tag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
