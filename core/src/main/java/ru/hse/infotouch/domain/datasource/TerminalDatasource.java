package ru.hse.infotouch.domain.datasource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import ru.hse.infotouch.domain.models.admin.QTerminal;
import ru.hse.infotouch.domain.models.admin.Terminal;
import ru.hse.infotouch.util.Strings;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TerminalDatasource {

    private final int pageSize = 30;

    private final QTerminal qTerminal = QTerminal.terminal;
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
}
