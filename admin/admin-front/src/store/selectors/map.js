import { filter, propEq, map, has, prop, indexBy, __ } from "ramda";

export const selectCurrentSchemeEdges    = state => {
    const pointsObj = indexBy(prop('id'), selectCurrentSchemePoints(state));
    const pointsHas = has(__, pointsObj);

    const edges = [ ...selectSchemeEdges(state), ...selectSchemeCreatedEdges(state) ];

    const filteredEdges = filter(edge => pointsHas(edge[ 0 ]), edges);

    return map(edge => ({
        p1: edge[ 0 ], // for id
        p2: edge[ 1 ], // for id
        x1: pointsObj[ edge[ 0 ] ].x,
        y1: pointsObj[ edge[ 0 ] ].y,
        x2: pointsObj[ edge[ 1 ] ].x,
        y2: pointsObj[ edge[ 1 ] ].y,
    }), filteredEdges);
};
export const selectCurrentSchemeElements = state => filterByCurrentSchemeId(state)(selectSchemeElements(state));
export const selectCurrentSchemePoints   = state => [
    ...filterByCurrentSchemeId(state)(selectSchemePoints(state)),
    ...selectSchemeCreatedPoints(state)
];

export const selectSchemeCreatedPoints = state => state.createdPoints.present.map(point => {
    return {
        x: point[ 0 ],
        y: point[ 1 ]
    }
});

export const selectSchemeCreatedEdges = state => state.createdEdges;
export const selectSchemeEdgesForSave = state => selectSchemeCreatedEdges(state).map(edge => ({
    leftPointId:   edge[ 0 ],
    rightPointId: edge[ 1 ],
    weight:        1
}));

const selectSchemeEdges    = state => selectMapData(state).edges;
const selectSchemeElements = state => selectMapData(state).elements;
const selectSchemePoints   = state => selectMapData(state).points;

const selectMapData                   = state => state.map.data;
export const selectMapCurrentSchemeId = state => state.map.buildingSchemeId;

const filterByCurrentSchemeId = state => filterBySchemeId(selectMapCurrentSchemeId(state));
const filterBySchemeId        = buildingSchemeId => array => filter(propEq('buildingSchemeId', buildingSchemeId), array);
