import { createAction } from "../../utils/action";
import { indexBy, prop } from "ramda";

export const SELECT_BUILDING = 'admin/device/SELECT_BUILDING';

export const LOAD_BUILDINGS         = 'admin/device/LOAD_BUILDINGS';
export const LOAD_BUILDINGS_SUCCESS = 'admin/device/LOAD_BUILDINGS_SUCCESS';
export const LOAD_BUILDINGS_FAILED  = 'admin/device/LOAD_BUILDINGS_FAILED';

export const selectBuilding = createAction(SELECT_BUILDING);
export const loadBuildings  = createAction(LOAD_BUILDINGS);

const buildings = (state = {}, action = {}) => {
    switch (action.type) {
        case LOAD_BUILDINGS_SUCCESS:

            return indexBy(prop('id'), action.payload);


        default:
            return state;
    }
};

export default buildings;