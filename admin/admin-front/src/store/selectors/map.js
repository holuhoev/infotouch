import { filter, propEq, map, has, prop, indexBy, __, find, isNil, isEmpty } from "ramda";
import { isRoomOrCorridor } from "../reducers/map";
import { isPointInPolygon } from "../../utils/map";
import { selectServiceByPointId, selectServiceIdByPointId } from "./services";

export const selectSchemes    = state => selectMapData(state).schemes;
export const selectBuildingId = state => state.application.selectedBuildingId;

export const selectCurrentSchemeEdges = state => {
    const currentSchemePointsObj = indexBy(prop('id'), selectCurrentSchemePoints(state));
    const pointHas               = has(__, currentSchemePointsObj);
    const edgeOnThisFloor        = edge => pointHas(edge[ 0 ]) && pointHas(edge[ 1 ]);

    const edges = [ ...selectSchemeEdges(state), ...selectSchemeCreatedEdges(state) ];

    const filteredEdges = filter(edgeOnThisFloor, edges);

    return map(edge => ({
        p1: edge[ 0 ], // for id
        p2: edge[ 1 ], // for id
        x1: currentSchemePointsObj[ edge[ 0 ] ].x,
        y1: currentSchemePointsObj[ edge[ 0 ] ].y,
        x2: currentSchemePointsObj[ edge[ 1 ] ].x,
        y2: currentSchemePointsObj[ edge[ 1 ] ].y,
    }), filteredEdges);
};

export const selectSchemeCreatedPoints     = state => state.createdPoints.present.map(point => {
    return {
        x:     point[ 0 ],
        y:     point[ 1 ],
        isNew: true
    }
});
export const selectCurrentSchemePoints     = state => [
    ...filterByCurrentSchemeId(state)(selectSchemePoints(state)),
    ...selectSchemeCreatedPoints(state)
];
const swapEdgePoints                       = edge => [ edge[ 1 ], edge[ 0 ] ];
export const selectCurrentSchemeStairPoint = state => {
    const currentSchemePointsObj = indexBy(prop('id'), selectCurrentSchemePoints(state));
    const pointHas               = has(__, currentSchemePointsObj);
    const edgeIsStairWithFirst   = edge => pointHas(edge[ 0 ]) && !pointHas(edge[ 1 ]);
    const edgeIsStairWithSecond  = edge => !pointHas(edge[ 0 ]) && pointHas(edge[ 1 ]);

    const edges = [ ...selectSchemeEdges(state), ...selectSchemeCreatedEdges(state) ];

    const stairWithFirst  = filter(edgeIsStairWithFirst, edges);
    const stairWithSecond = filter(edgeIsStairWithSecond, edges);


    const all = [
        ...stairWithFirst,
        ...stairWithSecond.map(swapEdgePoints)
    ];

    return indexBy(prop('id'), all.map(([ id1, id2 ]) => currentSchemePointsObj[ id1 ]));
};

export const selectPoints                = state => {
    const points      = selectCurrentSchemePoints(state);
    const stairPoints = selectCurrentSchemeStairPoint(state);

    return points.map(point => ({
        ...point,
        isInStair: !!stairPoints[ point.id ]
    }))
};
export const selectCurrentSchemeElements = state => filterByCurrentSchemeId(state)(selectSchemeElements(state));


export const selectSchemeCreatedEdges = state => state.createdEdges.present;
export const selectSchemeEdgesForSave = state => selectSchemeCreatedEdges(state).map(edge => ({
    leftPointId:  edge[ 0 ],
    rightPointId: edge[ 1 ],
    weight:       1
}));

const selectSchemeEdges    = state => selectMapData(state).edges;
const selectSchemeElements = state => selectMapData(state).elements;
const selectSchemePoints   = state => selectMapData(state).points;

const selectMapData                   = state => state.map.data;
export const selectMapCurrentSchemeId = state => state.map.buildingSchemeId;

const filterByCurrentSchemeId = state => filterBySchemeId(selectMapCurrentSchemeId(state));
const filterBySchemeId        = buildingSchemeId => array => filter(propEq('buildingSchemeId', buildingSchemeId), array);


export const selectRoomId = (state, [ x, y ]) => {
    const room = selectRoomOrCorridorByCoordinates(state, [ x, y ]);

    return room ? room.id : room
};

const selectRoomOrCorridorByCoordinates = (state, [ x, y ]) => {
    const all = selectCurrentSchemeElements(state).filter(isRoomOrCorridor);

    return getElementByCoordinates(all, [ x, y ]);
};

const getElementByCoordinates = (elements, [ x, y ]) => {
    return find(el => isNil(el.pointId) && isPointInPolygon(el.coordinates, [ x, y ]))(elements)
};

export const selectIsEmptyMap = (state) => {
    const schemes = selectSchemes(state);

    return isEmpty(schemes)
};

export const selectSelectedPoint = (state) => {
    const selectedPointId = state.map.selectedPointId;

    if (!selectedPointId)
        return null;

    const point   = find(propEq('id', selectedPointId))(selectCurrentSchemePoints(state));
    const service = selectServiceByPointId(state, selectedPointId);

    return {
        ...point,
        serviceId:    service ? service.id : null,
        serviceLabel: service ? `${ service.title }` : ''
    }
};