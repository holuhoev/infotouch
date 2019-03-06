package ru.hse.infotouch.feed.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.hse.infotouch.domain.*;
import ru.hse.infotouch.domain.enums.CityType;
import ru.hse.infotouch.feed.site.HsePersonService;
import ru.hse.infotouch.repo.*;
import ru.hse.infotouch.ruz.api.RuzApiService;
import ru.hse.infotouch.service.LecturerService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.hse.infotouch.domain.Chair.extractChairCity;
import static ru.hse.infotouch.domain.Chair.extractChairName;

@Component
public class RuzDomainLoader implements CommandLineRunner {
    private final RuzApiService ruzApi;

    private final LecturerService lecturerService;
    private final BuildingRepository buildingRepository;
    private final AuditoriumRepository auditoriumRepository;
    private final FacultyRepository facultyRepository;
    private final ChairRepository chairRepository;
    private final HsePersonService portalService;

    private final Logger logger = LoggerFactory.getLogger(RuzDomainLoader.class);

    @Autowired
    public RuzDomainLoader(RuzApiService ruzApi,
                           LecturerService lecturerService,
                           BuildingRepository buildingRepository,
                           AuditoriumRepository auditoriumRepository,
                           FacultyRepository facultyRepository,
                           ChairRepository chairRepository,
                           HsePersonService portalService) {
        this.ruzApi = ruzApi;
        this.lecturerService = lecturerService;
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
        setLecturersLinks();

//        loadBuildings();
//        loadAuditoriums();


    }

    private void setLecturersLinks() throws IOException {
        logger.info("Start setting lecturers links");
        List<Person> allHsePersons = portalService.getAllHsePersons();

        // 1. Чтобы найти сотрудника: Добавляем в Person: Lecturer
        // 2. Передаем person в lecturerService чтобы найти и заполнить поле lecturer
        // 3. Потом можно сгруппировать в два списка: преподы и сотрудники.
        // 4. Добавить в Person: должноть->факультет Map<String,List<String>>

        List<Lecturer> lecturersWithLink = allHsePersons.stream()
                .map(lecturerService::findByPersonAndSetLink)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        lecturerService.saveAll(lecturersWithLink);
        logger.info("Finish setting lecturers links. Count: {}", lecturersWithLink.size());
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

        List<Chair> chairsToSave = chairs.stream()
                .peek(chair -> {
                    chair.setCity(extractChairCity(chair));
                    chair.setName(extractChairName(chair));
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
        long t1_end = System.currentTimeMillis();
        logger.info("Get all lecturers from RUZ took: {} ms", t1_end - t1);

        lecturerService.saveAll(allLecturers);
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
