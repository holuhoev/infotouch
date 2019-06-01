package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.hse.infotouch.domain.models.admin.HseUnit;

public interface HseUnitRepository extends JpaRepository<HseUnit, Integer>, QuerydslPredicateExecutor<HseUnit> {
}
