package ru.hse.infotouch.domain.datasource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.hse.infotouch.domain.models.admin.Ad;
import ru.hse.infotouch.domain.models.admin.QAd;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class AdDatasource {

    @Value("${entities.page-size.default}")
    private int pageSize;

    private final EntityManager em;

    private final QAd qAd = QAd.ad;

    public AdDatasource(EntityManager em) {
        this.em = em;
    }

    public List<Ad> findAll(String searchString, int page) {
        BooleanBuilder whereClause = new BooleanBuilder();

        if (StringUtils.isNotEmpty(searchString)) {
            whereClause.and(qAd.title.containsIgnoreCase(searchString)
                    .or(qAd.content.containsIgnoreCase(searchString))
            );
        }
        return new JPAQuery<Ad>(em).select(qAd)
                .from(qAd)
                .where(whereClause)
                .offset(pageSize * page)
                .limit(page)
                .fetch();
    }
}
