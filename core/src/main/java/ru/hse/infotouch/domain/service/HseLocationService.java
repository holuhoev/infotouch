package ru.hse.infotouch.domain.service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.dto.request.HseLocationRequest;
import ru.hse.infotouch.domain.models.admin.HseLocation;
import ru.hse.infotouch.domain.repo.HseLocationRepository;

import java.util.List;

@Service
public class HseLocationService {
    private final HseLocationRepository repository;

    private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public HseLocationService(HseLocationRepository repository) {
        this.repository = repository;
    }

    public List<HseLocation> findAll() {
        return repository.findAll();
    }

    public HseLocation getOneById(int id) {
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Локации с id \"%d\" не существует", id)));
    }

    @Transactional
    public HseLocation create(HseLocationRequest request) {
        HseLocation hseLocation = new HseLocation();

        hseLocation.updateFromRequest(request)
                .setLocation(createPoint(request.getX(), request.getY()));

        return repository.save(hseLocation);
    }

    @Transactional
    public HseLocation update(int id, HseLocationRequest request) {
        HseLocation hseLocation = repository.getOne(id);

        hseLocation.updateFromRequest(request)
                .setLocation(createPoint(request.getX(), request.getY()));

        return repository.save(hseLocation);
    }


    @Transactional
    public void delete(int id) {
        HseLocation location = repository.getOne(id);

        repository.delete(location);
    }

    // TODO: move to util
    private Point createPoint(double x, double y) {
        return geometryFactory.createPoint(new Coordinate(x, y));
    }

}
