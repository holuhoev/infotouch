package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.hse.infotouch.domain.models.Employee;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID>, QuerydslPredicateExecutor<Employee> {
}

