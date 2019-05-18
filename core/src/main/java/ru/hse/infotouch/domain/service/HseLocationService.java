package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.datasource.HseLocationDatasource;
import ru.hse.infotouch.domain.dto.request.HseLocationRequest;
import ru.hse.infotouch.domain.models.admin.HseLocation;
import ru.hse.infotouch.domain.models.map.BuildingScheme;
import ru.hse.infotouch.domain.models.map.Point;
import ru.hse.infotouch.domain.repo.HseLocationRepository;
import ru.hse.infotouch.util.PostgresPointUtils;

import java.util.List;

@Service
public class HseLocationService {
    private final HseLocationRepository repository;
    private final HseLocationDatasource datasource;
    private final PointService pointService;
    private final BuildingSchemeService schemeService;
    private final PostgresPointUtils postgresPointUtils;


    public HseLocationService(HseLocationRepository repository,
                              HseLocationDatasource datasource,
                              PointService pointService,
                              BuildingSchemeService schemeService, PostgresPointUtils postgresPointUtils) {
        this.repository = repository;
        this.datasource = datasource;
        this.pointService = pointService;
        this.schemeService = schemeService;
        this.postgresPointUtils = postgresPointUtils;
    }

    public List<HseLocation> findAll(Integer buildingId) {
        return datasource.findAll(buildingId);
    }

    public HseLocation getOneById(int id) {
        HseLocation location = this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Локации с id \"%d\" не существует", id)));

        fetchFloorAndBuildingScheme(location);

        return location;
    }

    private HseLocation fetchFloorAndBuildingScheme(HseLocation location) {
        if (location.getPointId() != null) {
            Point point = pointService.getOneById(location.getPointId());

            if (point.getBuildingSchemeId() != null) {
                BuildingScheme scheme = schemeService.getOneById(point.getBuildingSchemeId());

                location.setBuildingSchemeId(scheme.getId());
                location.setFloor(scheme.getFloor());
            }
        }

        return location;
    }

    public HseLocation create(HseLocationRequest request) {
        HseLocation hseLocation = new HseLocation();

        return updateHseLocation(request, hseLocation);
    }

    public HseLocation update(int id, HseLocationRequest request) {
        HseLocation hseLocation = repository.getOne(id);

        return fetchFloorAndBuildingScheme(updateHseLocation(request, hseLocation));
    }

    private HseLocation updateHseLocation(HseLocationRequest request, HseLocation hseLocation) {
        hseLocation.updateFromRequest(request);

        if (request.getGpsX() != null && request.getGpsY() != null) {
            hseLocation.setLocation(postgresPointUtils.createPoint(request.getGpsX(), request.getGpsY()));
        }

        return repository.save(hseLocation);
    }


    public void delete(int id) {
        HseLocation location = repository.getOne(id);

        repository.delete(location);
    }
}
