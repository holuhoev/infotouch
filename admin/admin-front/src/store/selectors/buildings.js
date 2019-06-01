import { values } from 'ramda'

export const selectBuildings = state => values(state.buildings);

export const selectBuildingById = state => id => state.buildings[ id ];

export const selectBuildingNameById = state => id => selectBuildingById(state)(id) ? selectBuildingById(state)(id).name : null;