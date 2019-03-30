package ru.hse.infotouch.domain.datasource;

import ru.hse.infotouch.domain.models.admin.QUser;
import ru.hse.infotouch.domain.models.admin.relations.QUser2Terminal;
import ru.hse.infotouch.domain.models.admin.relations.User2Terminal;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

public class UserDatasource {
    private final EntityManager entityManager;

    private final QUser qUser = QUser.user;
    private final QUser2Terminal qUser2Terminal = QUser2Terminal.user2Terminal;

    public UserDatasource(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    // TODO:
    public List<User2Terminal> findAll(String searchString, int page) {

        return Collections.emptyList();
    }


}
