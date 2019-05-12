import polylabel from "@mapbox/polylabel";

function getPolygonInGEOJsonFormat(coordinates) {
    return [
        coordinates.split(" ")
            .map(point => point.split(",")
                .map(p => parseFloat(p)))
    ];
}

export const calculateCentroid = coordinates => {
    if (!coordinates)
        return null;

    const polygon = getPolygonInGEOJsonFormat(coordinates);

    return polylabel(polygon, 1.0)
};