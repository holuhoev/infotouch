package ru.hse.infotouch.domain.datasource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.hse.infotouch.domain.dto.UserTerminalDTO;
import ru.hse.infotouch.domain.models.admin.QTerminal;
import ru.hse.infotouch.domain.models.admin.Terminal;
import ru.hse.infotouch.domain.models.admin.relations.QUser2Terminal;
import ru.hse.infotouch.util.Strings;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class TerminalDatasource {

    @Value("${entities.page-size.default}")
    private int pageSize;

    private final QTerminal qTerminal = QTerminal.terminal;
    private final QUser2Terminal qUser2Terminal = QUser2Terminal.user2Terminal;

    private final EntityManager entityManager;

    public TerminalDatasource(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Terminal> findAll(String searchString, int page) {
        BooleanBuilder whereClause = new BooleanBuilder();

        if (StringUtils.isNotEmpty(searchString)) {
            String validated = Strings.removeRedundantSpace(searchString);

            whereClause.and(
                    qTerminal.title.containsIgnoreCase(validated)
                            .or(qTerminal.description.containsIgnoreCase(validated))
            );
        }

        JPAQuery<Terminal> query = new JPAQuery<>(entityManager);

        return query.from(qTerminal)
                .where(whereClause)
                .offset(pageSize * page)
                .limit(pageSize)
                .fetch();
    }

    public List<UserTerminalDTO> findAllByUserId(int userId) {

        List<Tuple> terminalAndRight = new JPAQuery<>(entityManager).select(qUser2Terminal.accessRight, qTerminal)
                .from(qUser2Terminal)
                .where(qUser2Terminal.id.userId.eq(userId))
                .leftJoin(qTerminal).on(qTerminal.id.eq(qUser2Terminal.id.terminalId))
                .fetch();

        return terminalAndRight.stream()
                .filter(tuple -> Objects.nonNull(tuple.get(qTerminal)))
                .map(tuple -> UserTerminalDTO.createOf(tuple.get(qTerminal), tuple.get(qUser2Terminal.accessRight)))
                .collect(Collectors.toList());
    }
}
