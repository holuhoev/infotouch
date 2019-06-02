import { filter, find, map, propEq } from "ramda";

import { selectBuildingNameById } from "./buildings";


const getUnitForList = state => unit => {
    const { buildingId, floor } = unit;

    return {
        ...unit,
        floorLabel:   floor ? `${ floor } этаж` : '',
        buildingName: selectBuildingNameById(state)(buildingId) || ''
    }
};

export const selectUnitList = state => {
    const units = state.units.list;

    return map(getUnitForList(state), units)
};

export const selectIsCreateEnable = state => !!state.application.selectedBuildingId && !state.application.unitsLoading;

export const selectUnitIdByElementId = (state, elementId) => {
    const unit = find(propEq('schemeElementId', elementId))(state.units.list);
    return unit ? unit.id : null;
};

export const selecteUnitsWithNoElement = state => {
    const units        = state.units.list;
    const selectedElementId = state.map.selectedElementId;

    return filter(s => (!s.schemeElementId || s.schemeElementId === selectedElementId), units);
};