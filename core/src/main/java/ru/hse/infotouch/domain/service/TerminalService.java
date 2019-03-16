package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
public class TerminalService {

    private final EntityManager entityManager;

    public TerminalService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void insertNewsRelations(int newsId, int[] terminalIds) {

        // TODO:
//        Query query = entityManager.createNativeQuery("insert into terminal2news (news_id,terminal_id) values (:newsId, :terminalId) ")
    }
}
