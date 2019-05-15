import polylabel from "@mapbox/polylabel";
import polygonContains from 'robust-point-in-polygon';
import { prop, range, sortBy } from "ramda";

const STAIRS_LINE_OFFSET = 4;

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

export const calculateStairsLines = stairsCoordinates => {
    const polygon = getPolygonAsArray(stairsCoordinates);


    const sorted = sortAngles(polygon);

    const zeroLine  = getZeroLine(sorted);
    const lineCount = getLineCount(sorted);

    return range(1, lineCount).map(cf => {
        return {
            x1: zeroLine[ 0 ][ 0 ],
            y1: zeroLine[ 0 ][ 1 ] + cf * STAIRS_LINE_OFFSET,
            x2: zeroLine[ 1 ][ 0 ],
            y2: zeroLine[ 1 ][ 1 ] + cf * STAIRS_LINE_OFFSET
        }
    });
};

const getZeroLine  = sorted => [ sorted[ 0 ], sorted[ 1 ] ];
const getLineCount = sorted => {
    const maxY = sorted[ 3 ][ 1 ];
    const minY = sorted[ 0 ][ 1 ];

    const length = maxY - minY;
    console.log(length);

    return length / STAIRS_LINE_OFFSET;
};

const sortByX    = sortBy(prop(0));
const sortByY    = sortBy(prop(1));
const sortAngles = list => sortByY(sortByX(list));