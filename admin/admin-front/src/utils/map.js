import polylabel from "@mapbox/polylabel";
import polygonContains from 'robust-point-in-polygon';

function getPolygonInGEOJsonFormat(coordinates) {
    return [
        coordinates.split(" ")
            .map(point => point.split(",").map(p => parseFloat(p)))
    ];
}

function getPolygonAsArray(coordinates) {
    return coordinates.split(" ")
            .map(point => point.split(",").map(p => parseFloat(p)))
}

export const calculateCentroid = coordinates => {
    if (!coordinates)
        return null;

    const polygon = getPolygonInGEOJsonFormat(coordinates);

    return polylabel(polygon, 1.0)
};

export const isPointInPolygon = (polygon, [ x, y ]) => {
    const coordinates = getPolygonAsArray(polygon);

    return polygonContains(coordinates, [ x, y ]) === -1;
};