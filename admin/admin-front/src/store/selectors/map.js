import { filter, propEq } from "ramda";


export const selectCurrentSchemeElements = state => filterByCurrentFloor(state)(selectSchemeElements(state));
export const selectCurrentSchemePoints   = state => filterByCurrentFloor(state)(selectSchemePoints(state));

const selectSchemeElements = state => selectMapData(state).elements;
const selectSchemePoints   = state => selectMapData(state).points;

const selectMapData         = state => state.map.data;
const selectMapCurrentFloor = state => state.map.floor;

const filterByCurrentFloor = state => filterByFloor(selectMapCurrentFloor(state));
const filterByFloor        = floor => array => filter(propEq('floor', floor), array);
