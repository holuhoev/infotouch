package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.models.map.MapElement;
import ru.hse.infotouch.domain.models.map.QMapElement;
import ru.hse.infotouch.domain.repo.MapElementRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MapElementService {

    private final MapElementRepository repository;
    private final QMapElement qMapElement = QMapElement.mapElement;

    public MapElementService(MapElementRepository repository) {
        this.repository = repository;
    }

    public List<MapElement> findAll(int buildingId) {

        return StreamSupport
                .stream(this.repository.findAll(qMapElement.buildingId.eq(buildingId)).spliterator(), false)
                .collect(Collectors.toList());
    }

    public MapElement getOneById(int id) {
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Элемента с id \"%d\" не существует", id)));
    }
}
