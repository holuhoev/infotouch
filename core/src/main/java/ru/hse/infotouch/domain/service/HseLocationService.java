package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.datasource.HseLocationDatasource;
import ru.hse.infotouch.domain.dto.request.HseLocationPointsRequest;
import ru.hse.infotouch.domain.dto.request.HseLocationRequest;
import ru.hse.infotouch.domain.models.admin.HseLocation;
import ru.hse.infotouch.domain.models.map.BuildingScheme;
import ru.hse.infotouch.domain.models.map.Point;
import ru.hse.infotouch.domain.models.map.SchemeElement;
import ru.hse.infotouch.domain.repo.HseLocationRepository;
import ru.hse.infotouch.util.PostgresPointUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Service
public class HseLocationService {
    private final HseLocationRepository repository;
    private final HseLocationDatasource datasource;
    private final PointService pointService;
    private final BuildingSchemeService schemeService;
    private final PostgresPointUtils postgresPointUtils;
    private final EntityManager entityManager;
    private final SchemeElementService schemeElementService;

    public HseLocationService(HseLocationRepository repository,
                              HseLocationDatasource datasource,
                              PointService pointService,
                              BuildingSchemeService schemeService, PostgresPointUtils postgresPointUtils, EntityManager entityManager, SchemeElementService schemeElementService) {
        this.repository = repository;
        this.datasource = datasource;
        this.pointService = pointService;
        this.schemeService = schemeService;
        this.postgresPointUtils = postgresPointUtils;
        this.entityManager = entityManager;
        this.schemeElementService = schemeElementService;
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

            if (point.getSchemeElementId() != null) {
                SchemeElement element = schemeElementService.getOneById(point.getSchemeElementId());
                BuildingScheme scheme = schemeService.getOneById(element.getBuildingSchemeId());

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

    @Transactional
    public void savePoints(HseLocationPointsRequest request) {
        Map<Integer, Integer> locationToPoint = request.getHseLocationToPoint();

        locationToPoint.entrySet()
                .stream()
                .map(entry -> {
                    Query query = entityManager.createNativeQuery("update hse_location set point_id=:pointId where id=:hseLocationId");
                    query.setParameter("pointId", entry.getValue())
                            .setParameter("hseLocationId", entry.getKey());

                    return query;
                }).forEach(Query::executeUpdate);
    }
}
