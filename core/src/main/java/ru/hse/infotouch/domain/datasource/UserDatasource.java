package ru.hse.infotouch.domain.datasource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.hse.infotouch.domain.models.admin.QUser;
import ru.hse.infotouch.domain.models.admin.User;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDatasource {

    @Value("${entities.page-size.default}")
    private int pageSize;

    private final EntityManager entityManager;

    private final QUser qUser = QUser.user;

    public UserDatasource(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<User> findAll(String searchString, int page) {

        BooleanBuilder whereClause = new BooleanBuilder();

        if (StringUtils.isNotEmpty(searchString)) {
            whereClause.and(
                    qUser.firstName.containsIgnoreCase(searchString)
                            .or(qUser.lastName.containsIgnoreCase(searchString))
                            .or(qUser.login.containsIgnoreCase(searchString))
            );
        }

        return new JPAQuery<>(entityManager).select(qUser)
                .from(qUser)
                .where(whereClause)
                .offset(page * pageSize)
                .limit(pageSize)
                .fetch();
    }


}
