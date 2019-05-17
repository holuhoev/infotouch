import { createAction } from "../../utils/action";
import { complement, filter, propEq } from "ramda";


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

// TODO: перенести в здания, сделать buildingSelector вместо deviceSelector.
export const SEARCH_DEVICE = 'admin/device/SEARCH_DEVICE';
export const SELECT_DEVICE = 'admin/device/SELECT_DEVICE';

export const createDevice     = createAction(OPEN_CREATE_DEVICE);
export const editDevice       = createAction(EDIT_DEVICE);
export const loadDevices      = createAction(LOAD_DEVICES);
export const loadDeviceById   = createAction(LOAD_ONE_DEVICE);
export const cancelEditDevice = createAction(CANCEL_EDIT_DEVICE);
export const saveDevice       = createAction(SAVE_DEVICE);
export const deleteDevice     = createAction(DELETE_DEVICE);
export const searchDevice     = createAction(SEARCH_DEVICE);
export const selectDevice     = createAction(SELECT_DEVICE);

const initState = {
    list:     [],
    editable: {}
};

export const devices = (state = initState, action = {}) => {

    switch (action.type) {

        case DELETE_DEVICE_SUCCESS:
            const notDeleted = complement(propEq('id', action.payload));

            return {
                ...state,
                list: filter(notDeleted, state.list)
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
