package ru.hse.infotouch.domain.datasource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.hse.infotouch.domain.datasource.util.ToManyFetcher;
import ru.hse.infotouch.domain.models.Person;
import ru.hse.infotouch.domain.models.QChair;
import ru.hse.infotouch.domain.models.QEmployee;
import ru.hse.infotouch.domain.models.QPerson;
import ru.hse.infotouch.domain.models.enums.CityType;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static ru.hse.infotouch.util.Strings.removeRedundantSpace;

@Repository
public class PersonDatasource {

    @Value("${entities.page-size.default}")
    private int pageSize;

    private final QPerson qPerson = QPerson.person;
    private final QEmployee qEmployee = QEmployee.employee;
    private final QChair qChair = QChair.chair;

    private final EntityManager entityManager;

    public PersonDatasource(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Person> findAll(String fio, UUID[] employeesIds, int page) {
        BooleanBuilder whereClause = new BooleanBuilder();

        JPAQuery<Person> query = new JPAQuery<>(entityManager).select(qPerson)
                .from(qPerson)
                .leftJoin(qEmployee).on(qPerson.id.eq(qEmployee.personId))
                .leftJoin(qChair).on(qEmployee.chairId.eq(qChair.Id));

        whereClause.and(qChair.city.eq(CityType.MOSCOW));

        if (StringUtils.isNotEmpty(fio)) {
            whereClause.and(qPerson.fio.containsIgnoreCase(removeRedundantSpace(fio)));
        }

        List<Person> persons = query.where(whereClause)
                .distinct()
                .offset(page * pageSize)
                .limit(pageSize)
                .fetch();

        List<Person> personWithEmployees = ToManyFetcher.forItems(persons)
                .by(Person::getId)
                .from(qEmployee)
                .joiningOn(qEmployee.personId)
                .fetchAndCombine(entityManager, Person::setEmployees);


        return personWithEmployees;
    }
}
