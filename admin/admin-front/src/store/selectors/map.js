import { filter, propEq } from "ramda";


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
const selectSchemeElements             = state => selectMapData(state).elements;
const selectSchemePoints               = state => selectMapData(state).points;

const selectMapData                   = state => state.map.data;
export const selectMapCurrentSchemeId = state => state.map.buildingSchemeId;

const filterByCurrentSchemeId = state => filterBySchemeId(selectMapCurrentSchemeId(state));
const filterBySchemeId        = buildingSchemeId => array => filter(propEq('buildingSchemeId', buildingSchemeId), array);
