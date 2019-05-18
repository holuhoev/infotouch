package ru.hse.infotouch.domain.service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.datasource.HseLocationDatasource;
import ru.hse.infotouch.domain.dto.request.HseLocationRequest;
import ru.hse.infotouch.domain.models.admin.HseLocation;
import ru.hse.infotouch.domain.repo.HseLocationRepository;

import java.util.List;

@Service
public class HseLocationService {
    private final HseLocationRepository repository;
    private final HseLocationDatasource datasource;

    private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public HseLocationService(HseLocationRepository repository,
                              HseLocationDatasource datasource) {
        this.repository = repository;
        this.datasource = datasource;
    }

    public List<HseLocation> findAll(Integer buildingId) {
        return datasource.findAll(buildingId);
    }

    public HseLocation getOneById(int id) {
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Локации с id \"%d\" не существует", id)));
    }

    public HseLocation create(HseLocationRequest request) {
        HseLocation hseLocation = new HseLocation();

        return updateHseLocation(request, hseLocation);
    }

    public HseLocation update(int id, HseLocationRequest request) {
        HseLocation hseLocation = repository.getOne(id);

        return updateHseLocation(request, hseLocation);
    }

    private HseLocation updateHseLocation(HseLocationRequest request, HseLocation hseLocation) {
        hseLocation.updateFromRequest(request);

        if (request.getGpsX() != null && request.getGpsY() != null) {
            hseLocation.setLocation(createPoint(request.getGpsX(), request.getGpsY()));
        }

        return repository.save(hseLocation);
    }


    public void delete(int id) {
        HseLocation location = repository.getOne(id);

        repository.delete(location);
    }

    // TODO: move to util
    private Point createPoint(double x, double y) {
        return geometryFactory.createPoint(new Coordinate(x, y));
    }

    public boolean isNotExist(int hseLocationId) {
        return !repository.existsById(hseLocationId);
    }
}
