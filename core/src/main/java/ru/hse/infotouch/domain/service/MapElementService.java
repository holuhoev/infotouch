package ru.hse.infotouch.domain.service;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.models.map.QSchemeElement;
import ru.hse.infotouch.domain.models.map.SchemeElement;
import ru.hse.infotouch.domain.models.map.QSchemeElement;
import ru.hse.infotouch.domain.repo.SchemeElementRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MapElementService {

    private final SchemeElementRepository repository;
    private final QSchemeElement qMapElement = QSchemeElement.schemeElement;

    public MapElementService(SchemeElementRepository repository) {
        this.repository = repository;
    }

    public List<SchemeElement> findAll(int[] schemeIds) {

        return StreamSupport
                .stream(this.repository.findAll(qMapElement.buildingSchemeId.in(ArrayUtils.toObject(schemeIds))).spliterator(), false)
                .collect(Collectors.toList());
    }

    public SchemeElement getOneById(int id) {
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Элемента с id \"%d\" не существует", id)));
    }
}
