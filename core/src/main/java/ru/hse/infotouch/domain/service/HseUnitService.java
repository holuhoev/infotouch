package ru.hse.infotouch.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.datasource.HseUnitDatasource;
import ru.hse.infotouch.domain.dto.request.HseUnitRequest;
import ru.hse.infotouch.domain.models.admin.HseUnit;
import ru.hse.infotouch.domain.models.map.BuildingScheme;
import ru.hse.infotouch.domain.models.map.SchemeElement;
import ru.hse.infotouch.domain.repo.HseUnitRepository;

import java.util.List;

@Service
public class HseUnitService {
    private final HseUnitDatasource datasource;
    private final HseUnitRepository repository;
    private final SchemeElementService schemeElementService;
    private final BuildingSchemeService schemeService;
    
    @Autowired
    public HseUnitService(HseUnitDatasource datasource, HseUnitRepository repository, SchemeElementService schemeElementService, BuildingSchemeService schemeService) {
        this.datasource = datasource;
        this.repository = repository;
        this.schemeElementService = schemeElementService;
        this.schemeService = schemeService;
    }

    public List<HseUnit> findAll(Integer buildingId) {
        return datasource.findAll(buildingId);
    }

    public HseUnit getOneById(int id) {
        HseUnit unit = this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Подразделения с id \"%d\" не существует", id)));

        fetchFloorAndBuildingScheme(unit);

        return unit;
    }

    private HseUnit fetchFloorAndBuildingScheme(HseUnit unit) {
        if (unit.getSchemeElementId() != null) {
            SchemeElement element = schemeElementService.getOneById(unit.getSchemeElementId());

            if (element.getBuildingSchemeId() != null) {
                BuildingScheme scheme = schemeService.getOneById(element.getBuildingSchemeId());

                unit.setBuildingSchemeId(scheme.getId());
                unit.setFloor(scheme.getFloor());
            }
        }

        return unit;
    }

    public HseUnit create(HseUnitRequest request) {
        HseUnit hseUnit = new HseUnit();

        return updateHseUnit(request, hseUnit);
    }

    public HseUnit update(int id, HseUnitRequest request) {
        HseUnit hseUnit = repository.getOne(id);

        return fetchFloorAndBuildingScheme(updateHseUnit(request, hseUnit));
    }

    private HseUnit updateHseUnit(HseUnitRequest request, HseUnit hseUnit) {
        hseUnit.updateFromRequest(request);

        return repository.save(hseUnit);
    }


    public void delete(int id) {
        HseUnit unit = repository.getOne(id);

        repository.delete(unit);
    }
}
