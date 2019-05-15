package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.models.map.BuildingScheme;
import ru.hse.infotouch.domain.models.map.QBuildingScheme;
import ru.hse.infotouch.domain.repo.BuildingSchemeRepository;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BuildingSchemeService {
    private final BuildingSchemeRepository repository;
    private final QBuildingScheme qBuildingScheme = QBuildingScheme.buildingScheme;

    public BuildingSchemeService(BuildingSchemeRepository repository) {
        this.repository = repository;
    }

    public List<BuildingScheme> findAll(int buildingId) {
        Iterable<BuildingScheme> filtered = repository.findAll(qBuildingScheme.buildingId.eq(buildingId));

        return StreamSupport.stream(filtered.spliterator(), false)
                .collect(Collectors.toList());
    }

    public BuildingScheme getOneById(int id) {
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Схемы с id \"%d\" не существует", id)));
    }
}
