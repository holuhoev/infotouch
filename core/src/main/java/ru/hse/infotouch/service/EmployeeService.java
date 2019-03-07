package ru.hse.infotouch.service;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.Employee;
import ru.hse.infotouch.domain.QEmployee;
import ru.hse.infotouch.repo.EmployeeRepository;

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
