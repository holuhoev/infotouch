package ru.hse.infotouch.service;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.*;
import ru.hse.infotouch.domain.dto.EmployeeHseDTO;
import ru.hse.infotouch.domain.dto.PersonHseDTO;
import ru.hse.infotouch.repo.ChairRepository;
import ru.hse.infotouch.repo.LecturerRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.apache.commons.lang3.StringUtils.stripToEmpty;

@Service
public class LecturerService {

    private final LecturerRepository repository;
    private final ChairRepository chairRepository;

    private final QChair qChair = QChair.chair;
    private final QLecturer qLecturer = QLecturer.lecturer;

    private static String removeRedundantSpace(String searchString) {
        return Arrays.stream(searchString.split(" "))
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(" "));
    }

    @Autowired
    public LecturerService(LecturerRepository repository, ChairRepository chairRepository) {
        this.repository = repository;
        this.chairRepository = chairRepository;
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
                        findExactChair(employee.getChairName(), employee.getFacultyName()).ifPresent(chair -> {
                            employee.setChairId(chair.getId());
                        });
                    }
                })
                .collect(Collectors.toList());

        personHseDTO.setEmployees(filledEmployees);

        return personHseDTO;
    }


    private Optional<Chair> findExactChair(String chairName, String facultyName) {

        return StreamSupport.stream(
                chairRepository.findAll(
                        qChair.name.containsIgnoreCase(stripToEmpty(chairName))
                                .and(qChair.facultyName.containsIgnoreCase(stripToEmpty(facultyName)))
                ).spliterator(), false)
                .findFirst();
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