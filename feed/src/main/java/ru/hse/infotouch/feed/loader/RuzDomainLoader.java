package ru.hse.infotouch.feed.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.hse.infotouch.domain.*;
import ru.hse.infotouch.domain.dto.PersonHseDTO;
import ru.hse.infotouch.domain.models.*;
import ru.hse.infotouch.domain.models.enums.CityType;
import ru.hse.infotouch.feed.site.HsePersonService;
import ru.hse.infotouch.domain.repo.*;
import ru.hse.infotouch.ruz.api.RuzApiService;
import ru.hse.infotouch.domain.service.EmployeeService;
import ru.hse.infotouch.domain.service.LecturerService;
import ru.hse.infotouch.domain.service.PersonService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static ru.hse.infotouch.domain.models.Chair.extractChairCity;
import static ru.hse.infotouch.domain.models.Chair.extractChairName;

@Component
public class RuzDomainLoader implements CommandLineRunner {
    private final RuzApiService ruzApi;

    private final LecturerService lecturerService;
    private final PersonService personService;
    private final EmployeeService employeeService;
    private final BuildingRepository buildingRepository;
    private final AuditoriumRepository auditoriumRepository;
    private final FacultyRepository facultyRepository;
    private final ChairRepository chairRepository;
    private final HsePersonService portalService;

    private final QChair qChair = QChair.chair;

    private final Logger logger = LoggerFactory.getLogger(RuzDomainLoader.class);

    @Autowired
    public RuzDomainLoader(RuzApiService ruzApi,
                           LecturerService lecturerService,
                           PersonService personService, EmployeeService employeeService, BuildingRepository buildingRepository,
                           AuditoriumRepository auditoriumRepository,
                           FacultyRepository facultyRepository,
                           ChairRepository chairRepository,
                           HsePersonService portalService) {
        this.ruzApi = ruzApi;
        this.lecturerService = lecturerService;
        this.personService = personService;
        this.employeeService = employeeService;
        this.buildingRepository = buildingRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.facultyRepository = facultyRepository;
        this.chairRepository = chairRepository;
        this.portalService = portalService;
    }


    @Override
    public void run(String... args) throws Exception {
//        loadFaculties();
//        loadChairs();
//        loadLecturers();
//        loadPersons();

//        loadBuildings();
//        loadAuditoriums()
    }

    private void loadPersons() throws IOException {
        logger.info("Start setting lecturers links");
        employeeService.deleteAll();
        personService.deleteAll();

        List<PersonHseDTO> allHsePersons = portalService.getAllHsePersons();

        List<Person> personsToSave = allHsePersons.stream()
                .map(lecturerService::fillEmployees)
                .map(Person::ofHseDto)
                .collect(Collectors.toList());

        personService.saveAll(personsToSave);

        List<Employee> employeesToSave = personsToSave.stream()
                .flatMap(person -> person.getEmployees().stream())
                .collect(Collectors.toList());

        employeeService.saveAll(employeesToSave);

        logger.info("Finish persons={} and employees={}", personsToSave.size(), employeesToSave.size());
    }

    private void loadFaculties() {
        logger.info("Faculties loader starts");
        facultyRepository.deleteAll();

        Faculty empty = new Faculty();
        empty.setId(0);
        empty.setName("Не определен");
        facultyRepository.save(empty);

        List<Faculty> faculties = ruzApi.getAllFaculties();


        facultyRepository.saveAll(faculties);
        logger.info("Faculties loader ends");
    }

    private void loadChairs() {
        logger.info("Chairs loader starts");
        chairRepository.deleteAll();

        List<Chair> chairs = ruzApi.getAllChairs();


        Map<Integer, Faculty> faculties = facultyRepository.findAll().stream()
                .collect(Collectors.toMap(Faculty::getId, identity()));


        List<Chair> chairsToSave = chairs.stream()
                .peek(chair -> {
                    chair.setCity(extractChairCity(chair));
                    chair.setName(extractChairName(chair));

                    if (faculties.containsKey(chair.getFacultyId())) {
                        Faculty faculty = faculties.get(chair.getFacultyId());

                        chair.setFacultyName(faculty.getName());
                    }
                })
                .collect(Collectors.toList());

        chairRepository.saveAll(chairsToSave);
        logger.info("Chairs loader ends");
    }

    private void loadLecturers() {
        lecturerService.deleteAll();
        logger.info("Lecturer loader starts");

        long t1 = System.currentTimeMillis();

        List<Lecturer> allLecturers = ruzApi.getAllLecturers();


        Map<Integer, Chair> chairs = chairRepository.findAll().stream()
                .collect(Collectors.toMap(Chair::getId, identity()));

        Map<Integer, Faculty> faculties = facultyRepository.findAll().stream()
                .collect(Collectors.toMap(Faculty::getId, identity()));


        long t1_end = System.currentTimeMillis();
        logger.info("Get all lecturers from RUZ took: {} ms", t1_end - t1);

        List<Lecturer> lecturerToSave = allLecturers.stream()
                .peek(lecturer -> {
                    if (chairs.containsKey(lecturer.getChairId())) {
                        Chair chair = chairs.get(lecturer.getChairId());
                        lecturer.setChairName(chair.getName());
                        lecturer.setChairCity(chair.getCity());

                        if (faculties.containsKey(chair.getFacultyId())) {
                            Faculty faculty = faculties.get(chair.getFacultyId());

                            lecturer.setFacultyName(faculty.getName());
                        }
                    }
                })
                .collect(Collectors.toList());

        lecturerService.saveAll(lecturerToSave);
        long end = System.currentTimeMillis();
        logger.info("Save all lecturers took: {} ms", end - t1_end);
    }

    private void loadBuildings() {
        buildingRepository.deleteAll();
        logger.info("Buildings loader starts");
        long t1 = System.currentTimeMillis();

        List<Building> allBuildings = ruzApi.getAllBuildings();

        long t1_end = System.currentTimeMillis();
        logger.info("Get all buildings from RUZ took: {} ms", t1_end - t1);

        List<Building> buildingsToSave = allBuildings.stream()
                .peek(building -> building.setCity(CityType.ofBuildingAddress(building.getAddress())))
                .collect(Collectors.toList());

        buildingRepository.saveAll(buildingsToSave);

        long end = System.currentTimeMillis();
        logger.info("Save all buildings took: {} ms", end - t1_end);
    }

    private void loadAuditoriums() {
        auditoriumRepository.deleteAll();

        logger.info("Auditoriums loader starts");
        List<Auditorium> allAuditoriums = ruzApi.getAllAuditoriums();

        auditoriumRepository.saveAll(allAuditoriums);
        logger.info("Save all auditoriums");
    }
}
