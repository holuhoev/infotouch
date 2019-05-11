package ru.hse.infotouch.feed.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.hse.infotouch.domain.models.enums.MapElementType;
import ru.hse.infotouch.domain.models.map.MapElement;
import ru.hse.infotouch.domain.repo.MapElementRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapParser implements CommandLineRunner {

    private final MapElementRepository mapElementRepository;

    public MapParser(MapElementRepository mapElementRepository) {
        this.mapElementRepository = mapElementRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String elements = " <polygon id=\"433b\" points=\"0.700382475 2.20620378 85.8150245 2.20620378 85.8150245 105.205309 0.700382475 105.205309\"></polygon>\n" +
                "            <polygon id=\"433\" points=\"85.7583678 2.50125932 85.7583678 105.095432 141.105243 105.095432 141.105243 2.50125932\"></polygon>\n" +
                "            <polygon id=\"433a\" points=\"141.092759 105.210765 141.092759 3.1164327 202.035389 3.1164327 202.035389 105.210765\"></polygon>\n" +
                "            <polygon id=\"431a\" points=\"202.057694 104.920164 202.057694 3.08761235 257.186338 3.08761235 257.186338 104.920164\"></polygon>\n" +
                "            <polygon id=\"431\" points=\"257.036364 105.03045 257.036364 0.297115626 314.028259 0.297115626 314.028259 105.03045\"></polygon>\n" +
                "            <polygon id=\"429\" points=\"314.00771 0.0695426837 314.00771 111.025584 364.793127 111.025584 364.793127 0.0695426837\"></polygon>\n" +
                "            <polygon id=\"427\" points=\"365.118929 0.0928698898 365.118929 111.07097 416.089364 111.07097 416.089364 0.0928698898\"></polygon>\n" +
                "            <polygon id=\"425\" points=\"416.132482 0.152807001 416.132482 117.47928 503.438197 117.47928 503.438197 58.8160433 493.372506 58.8160433 493.372506 0.152807001\"></polygon>\n" +
                "            <polygon id=\"corridor\" points=\"0.877199111 105.256578 0.877199111 156.569189 40.4639264 156.569189 40.4639264 146.857052 312.934487 142.617937 312.934487 151.196145 502.945612 151.196145 502.945612 117.361549 416.171246 117.361549 416.171246 111.138683 314.043422 111.138683 314.043422 105.256578\"></polygon>\n" +
                "       ";

        Document doc = Jsoup.parse(elements);

        List<MapElement> mapElementList = doc.select("polygon")
                .stream()
                .map(polygon -> {
                    MapElement mapElement = new MapElement();

                    String info = polygon.attr("id");
                    String points = polygon.attr("points");

                    mapElement.setBuildingId(2167);
                    mapElement.setCoordinates(mapPointsToCoordinates(points));
                    mapElement.setLabel(info);
                    mapElement.setFloor(3);
                    mapElement.setType(MapElementType.ofString(info));

                    return mapElement;
                }).collect(Collectors.toList());


        mapElementRepository.saveAll(mapElementList);
    }

    private String mapPointsToCoordinates(String points) {
        String[] allPoints = points.split(" ");
        String[] coordinates = new String[allPoints.length / 2];

        for (int i = 0; i < allPoints.length - 1; i += 2) {
            coordinates[i / 2] = allPoints[i] + "," + allPoints[i + 1];
        }

        return String.join(" ", coordinates);
    }
}
