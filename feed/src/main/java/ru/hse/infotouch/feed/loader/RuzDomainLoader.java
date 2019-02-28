package ru.hse.infotouch.feed.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.hse.infotouch.domain.*;
import ru.hse.infotouch.domain.enums.CityType;
import ru.hse.infotouch.repo.*;
import ru.hse.infotouch.ruz.api.RuzApiService;
import ru.hse.infotouch.service.LecturerService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RuzDomainLoader implements CommandLineRunner {
    private final RuzApiService ruzApi;

    private final LecturerService lecturerService;
    private final BuildingRepository buildingRepository;
    private final AuditoriumRepository auditoriumRepository;
    private final FacultyRepository facultyRepository;
    private final ChairRepository chairRepository;

    private final Logger logger = LoggerFactory.getLogger(RuzDomainLoader.class);

    @Autowired
    public RuzDomainLoader(RuzApiService ruzApi,
                           LecturerService lecturerService,
                           BuildingRepository buildingRepository,
                           AuditoriumRepository auditoriumRepository,
                           FacultyRepository facultyRepository,
                           ChairRepository chairRepository) {
        this.ruzApi = ruzApi;
        this.lecturerService = lecturerService;
        this.buildingRepository = buildingRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.facultyRepository = facultyRepository;
        this.chairRepository = chairRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadFaculties();
        loadChairs();
        loadLecturers();

        loadBuildings();
        loadAuditoriums();
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

        chairRepository.saveAll(chairs);
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
                .peek(building -> building.setCity(CityType.of(building.getAddress())))
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
