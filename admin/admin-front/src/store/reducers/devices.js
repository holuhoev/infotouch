import { createAction } from "../../utils/action";
import { excludeById } from "../../utils/common";


export const LOAD_DEVICES         = 'admin/device/LOAD_DEVICES';
export const LOAD_DEVICES_SUCCESS = 'admin/device/LOAD_DEVICES_SUCCESS';
export const LOAD_DEVICES_FAILED  = 'admin/device/LOAD_DEVICES_FAILED';

export const OPEN_CREATE_DEVICE = 'admin/device/OPEN_CREATE_DEVICE';

export const LOAD_ONE_DEVICE         = 'admin/device/LOAD_ONE_DEVICE';
export const LOAD_ONE_DEVICE_SUCCESS = 'admin/device/LOAD_ONE_DEVICE_SUCCESS';
export const LOAD_ONE_DEVICE_FAILED  = 'admin/device/LOAD_ONE_DEVICE_FAILED';

export const CANCEL_EDIT_DEVICE = 'admin/device/CANCEL_EDIT_DEVICE';

export const SAVE_DEVICE             = 'admin/device/SAVE_DEVICE';
export const SAVE_DEVICE_SUCCESS     = 'admin/device/SAVE_DEVICE_SUCCESS';
export const SAVE_NEW_DEVICE_SUCCESS = 'admin/device/SAVE_NEW_DEVICE_SUCCESS';
export const SAVE_DEVICE_FAILED      = 'admin/device/SAVE_DEVICE_FAILED';

export const EDIT_DEVICE           = 'admin/device/EDIT_DEVICE';
export const DELETE_DEVICE         = 'admin/device/DELETE_DEVICE';
export const DELETE_DEVICE_SUCCESS = 'admin/device/DELETE_DEVICE_SUCCESS';
export const DELETE_DEVICE_FAILED  = 'admin/device/DELETE_DEVICE_FAILED';

export const SELECT_DEVICE = 'admin/device/SELECT_DEVICE';

export const CHANGE_DEVICE_POINT = 'admin/device/CHANGE_DEVICE_POINT';
export const REMOVE_DEVICE_POINT = 'admin/device/REMOVE_DEVICE_POINT';


export const createDevice      = createAction(OPEN_CREATE_DEVICE);
export const editDevice        = createAction(EDIT_DEVICE);
export const loadDevices       = createAction(LOAD_DEVICES);
export const loadDeviceById    = createAction(LOAD_ONE_DEVICE);
export const cancelEditDevice  = createAction(CANCEL_EDIT_DEVICE);
export const saveDevice        = createAction(SAVE_DEVICE);
export const deleteDevice      = createAction(DELETE_DEVICE);
export const selectDevice      = createAction(SELECT_DEVICE);
export const changeDevicePoint = createAction(CHANGE_DEVICE_POINT);
export const removeDevicePoint = createAction(REMOVE_DEVICE_POINT);


const initState = {
    list:     [],
    editable: {}
};

const device = (state = {}, action = {}) => {
    switch (action.type) {

        case CHANGE_DEVICE_POINT:
            if (action.payload.pointId === state.pointId) {

                return {
                    ...state,
                    pointId: null
                }
            }

            if (action.payload.deviceId === state.id.toString()) {

                return {
                    ...state,
                    pointId: action.payload.pointId
                }
            }
            return state;

        case REMOVE_DEVICE_POINT:
            if (action.payload === state.pointId) {

                return {
                    ...state,
                    pointId: null
                }
            }
            return state;

        default:
            return state;
    }
};

export const devices = (state = initState, action = {}) => {

    switch (action.type) {

        case CHANGE_DEVICE_POINT:
        case REMOVE_DEVICE_POINT:
            return {
                ...state,
                list: state.list.map(s => device(s, action))
            };

        case DELETE_DEVICE_SUCCESS:

            return {
                ...state,
                list: excludeById(action.payload, state.list)
            };

        case OPEN_CREATE_DEVICE:

            return {
                ...state,
                editable: {
                    isNew: true
                }
            };

        case LOAD_DEVICES_SUCCESS:

            return {
                ...state,
                list: action.payload
            };
        case LOAD_ONE_DEVICE_SUCCESS:
        case LOAD_ONE_DEVICE:
            return {
                ...state,
                editable: action.payload
            };

        case CANCEL_EDIT_DEVICE:

            return {
                ...state,
                editable: {}
            };

        case SAVE_NEW_DEVICE_SUCCESS:

            return {
                ...state,
                editable: {},
                list:     [
                    ...state.list,
                    action.payload
                ]
            };
        case SAVE_DEVICE_SUCCESS:

            return {
                ...state,
                editable: {},
                list:     [
                    ...state.list.map(device => {
                        if (device.id === action.payload.id) {
                            return action.payload;
                        }
                        return device;
                    }),
                ]
            };

        case EDIT_DEVICE:

            return {
                ...state,
                editable: {
                    ...state.editable,
                    ...action.payload
                }
            };
        default:
            return state;
    }
};
