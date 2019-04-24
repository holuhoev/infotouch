package ru.hse.infotouch.domain.models.admin;

import org.jsoup.nodes.Element;

import javax.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String title;

    @Column
    private String color;

    public Topic(String title, String color) {
        this.title = title;
        this.color = color;
    }

    public Topic() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return Objects.equals(id, topic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static String getColorOfClassname(Element topicElement) {
        if (topicElement.hasClass("rubric_18")) {
            return "#d8675b";
        }
        if (topicElement.hasClass("rubric_41")) {
            return "#9472ae";
        }

        if (topicElement.hasClass("rubric_24")) {
            return "#d4af79";
        }

        return "";
    }
}
