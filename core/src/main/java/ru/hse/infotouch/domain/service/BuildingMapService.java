package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.datasource.PointDatasource;
import ru.hse.infotouch.domain.dto.BuildingMapDTO;
import ru.hse.infotouch.domain.models.Room;
import ru.hse.infotouch.domain.models.map.Edge;
import ru.hse.infotouch.domain.models.map.Point;

import java.util.List;

import static ru.hse.infotouch.util.DomainObjectUtils.getIds;

@Service
public class BuildingMapService {

    private final EdgeService edgeService;
    private final RoomService roomService;
    private final PointDatasource pointDatasource;

    public BuildingMapService(EdgeService edgeService, RoomService roomService, PointDatasource pointDatasource) {
        this.edgeService = edgeService;
        this.roomService = roomService;
        this.pointDatasource = pointDatasource;
    }

    public BuildingMapDTO getOne(int buildingId) {
        BuildingMapDTO result = new BuildingMapDTO();

        List<Room> rooms = roomService.findAll(buildingId);
        List<Point> points = pointDatasource.findAll(getIds(rooms));
        List<Edge> edges = edgeService.findAll(getIds(points));

        result.setBuildingId(buildingId);
        result.setRooms(rooms);
        result.setPoints(points);
        result.setEdges(edges);

        return result;
    }
}
