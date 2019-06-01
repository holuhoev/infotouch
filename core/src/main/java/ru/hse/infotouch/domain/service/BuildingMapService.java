package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.datasource.PointDatasource;
import ru.hse.infotouch.domain.dto.BuildingMapDTO;
import ru.hse.infotouch.domain.models.map.BuildingScheme;
import ru.hse.infotouch.domain.models.map.Edge;
import ru.hse.infotouch.domain.models.map.SchemeElement;
import ru.hse.infotouch.domain.models.map.Point;

import java.util.List;

import static ru.hse.infotouch.util.DomainObjectUtils.getIds;

@Service
public class BuildingMapService {

    private final EdgeService edgeService;
    private final SchemeElementService schemeElementService;
    private final PointDatasource pointDatasource;
    private final BuildingSchemeService buildingSchemeService;

    public BuildingMapService(EdgeService edgeService, SchemeElementService schemeElementService, PointDatasource pointDatasource, BuildingSchemeService buildingSchemeService) {
        this.edgeService = edgeService;
        this.schemeElementService = schemeElementService;
        this.pointDatasource = pointDatasource;
        this.buildingSchemeService = buildingSchemeService;
    }

    public BuildingMapDTO getOne(int buildingId) {
        BuildingMapDTO result = new BuildingMapDTO();

        List<BuildingScheme> schemes = buildingSchemeService.findAll(buildingId);
        List<SchemeElement> elements = schemeElementService.findAll(getIds(schemes));
        List<Point> points = pointDatasource.findAll(getIds(elements));
        List<Edge> edges = edgeService.findAll(getIds(points));

        result.setSchemes(schemes);
        result.setBuildingId(buildingId);
        result.setElements(elements);
        result.setPoints(points);
        result.setEdges(edges);

        return result;
    }
}
