package ru.hse.infotouch.domain.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.hse.infotouch.domain.dto.EmployeeHseDTO;
import ru.hse.infotouch.domain.dto.PersonHseDTO;
import ru.hse.infotouch.domain.models.Lecturer;
import ru.hse.infotouch.domain.models.QLecturer;
import ru.hse.infotouch.domain.repo.LecturerRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.apache.commons.lang3.StringUtils.stripToEmpty;
import static ru.hse.infotouch.util.Strings.removeRedundantSpace;

@Service
public class LecturerService {

    private final LecturerRepository repository;
    private final ChairService chairService;

    private final QLecturer qLecturer = QLecturer.lecturer;

    @Autowired
    public LecturerService(LecturerRepository repository, ChairService chairService) {
        this.repository = repository;
        this.chairService = chairService;
    }

    public List<Lecturer> findAllBy(String searchString) {
        Objects.requireNonNull(searchString);

        if (searchString.length() == 0) {
            throw new IllegalArgumentException("searchString must not be empty");
        }

        String validatedSearchString = removeRedundantSpace(searchString);

        return Lists.newArrayList(repository.findAll(qLecturer.fio.containsIgnoreCase(validatedSearchString)));
    }


    public void saveAll(Iterable<Lecturer> lecturers) {
        repository.saveAll(lecturers);
    }

    public void deleteAll() {
        repository.deleteAll();
    }


    public PersonHseDTO fillEmployees(PersonHseDTO personHseDTO) {
        List<EmployeeHseDTO> filledEmployees = personHseDTO.getEmployees().stream()
                .peek(employee -> this.findExact(employee, personHseDTO).ifPresent(lecturer -> {
                    employee.setLecturerId(lecturer.getId());
                    employee.setChairId(lecturer.getChairId());
                }))
                .peek(employee -> {
                    if (Objects.isNull(employee.getLecturerId())) {
                        chairService.findExactChair(employee.getChairName(), employee.getFacultyName()).ifPresent(chair -> {
                            employee.setChairId(chair.getId());
                        });
                    }
                })
                .collect(Collectors.toList());

        personHseDTO.setEmployees(filledEmployees);

        return personHseDTO;
    }


    private Optional<Lecturer> findExact(EmployeeHseDTO dto, PersonHseDTO person) {
        if (Objects.isNull(person.getFio())) {
            return Optional.empty();
        }

        return StreamSupport.stream(
                repository.findAll(
                        qLecturer.chairName.containsIgnoreCase(stripToEmpty(dto.getChairName()))
                                .and(qLecturer.facultyName.containsIgnoreCase(stripToEmpty(dto.getFacultyName())))
                                .and(qLecturer.fio.containsIgnoreCase(person.getFio()))
                                .and(qLecturer.chairCity.eq(person.getCity()))
                ).spliterator(), false)
                .findFirst();
    }
}