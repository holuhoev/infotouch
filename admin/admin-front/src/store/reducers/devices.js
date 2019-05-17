import { createAction } from "../../utils/action";


export const LOAD_DEVICES         = 'admin/device/LOAD_DEVICES';
export const LOAD_DEVICES_SUCCESS = 'admin/device/LOAD_DEVICES_SUCCESS';
export const LOAD_DEVICES_FAILED  = 'admin/device/LOAD_DEVICES_FAILED';

export const loadDevices = createAction(LOAD_DEVICES);

const initState = [];

export const devices = (state = initState, action = {}) => {

    switch (action.type) {
        case LOAD_DEVICES_SUCCESS:
            return action.payload;

        default:
            return state;
    }
};
