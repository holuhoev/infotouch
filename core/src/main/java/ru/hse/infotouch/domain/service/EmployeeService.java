package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.models.Employee;
import ru.hse.infotouch.domain.models.QEmployee;
import ru.hse.infotouch.domain.repo.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    private final QEmployee qEmployee = QEmployee.employee;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public void saveAll(List<Employee> employees) {
        repository.saveAll(employees);
    }

    public boolean isLecturerExists(int lecturerId) {
        return repository.exists(qEmployee.lecturerId.eq(lecturerId));
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
