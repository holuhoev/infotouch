package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.datasource.PointDatasource;
import ru.hse.infotouch.domain.dto.BuildingMapDTO;
import ru.hse.infotouch.domain.models.map.Edge;
import ru.hse.infotouch.domain.models.map.MapElement;
import ru.hse.infotouch.domain.models.map.Point;

import java.util.List;

import static ru.hse.infotouch.util.DomainObjectUtils.getIds;

@Service
public class BuildingMapService {

    private final EdgeService edgeService;
    private final MapElementService mapElementService;
    private final PointDatasource pointDatasource;

    public BuildingMapService(EdgeService edgeService, MapElementService mapElementService, PointDatasource pointDatasource) {
        this.edgeService = edgeService;
        this.mapElementService = mapElementService;
        this.pointDatasource = pointDatasource;
    }

    public BuildingMapDTO getOne(int buildingId) {
        BuildingMapDTO result = new BuildingMapDTO();

        List<MapElement> elements = mapElementService.findAll(buildingId);
        List<Point> points = pointDatasource.findAll(getIds(elements));
        List<Edge> edges = edgeService.findAll(getIds(points));

        result.setBuildingId(buildingId);
        result.setElements(elements);
        result.setPoints(points);
        result.setEdges(edges);

        return result;
    }
}
