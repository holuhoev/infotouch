import { map } from "ramda";

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