import {
    CANCEL_EDIT_DEVICE,
    LOAD_DEVICES,
    LOAD_DEVICES_FAILED,
    LOAD_DEVICES_SUCCESS,
    LOAD_ONE_DEVICE, LOAD_ONE_DEVICE_FAILED,
    LOAD_ONE_DEVICE_SUCCESS, SAVE_DEVICE, SAVE_DEVICE_FAILED, SAVE_DEVICE_SUCCESS
} from "./devices";

const initialState = {
    listLoading:  false,
    oneLoading:   false,
    error:        null,
    visibleModal: false,
    saveLoading:  false
};

export const application = (state = initialState, action = {}) => {
    switch (action.type) {

        case LOAD_DEVICES:

            return {
                ...state,
                listLoading: true
            };

        case LOAD_DEVICES_SUCCESS:

            return {
                ...state,
                listLoading: false
            };

        case LOAD_DEVICES_FAILED:

            return {
                ...state,
                listLoading: false,
                error:       action.payload
            };

        case LOAD_ONE_DEVICE:

            return {
                ...state,
                visibleModal: true,
                oneLoading:   true
            };

        case LOAD_ONE_DEVICE_SUCCESS:

            return {
                ...state,
                oneLoading: false
            };

        case LOAD_ONE_DEVICE_FAILED:

            return {
                ...state,
                oneLoading: false,
                error:      action.payload
            };

        case CANCEL_EDIT_DEVICE:

            return {
                ...state,
                visibleModal: false
            };

        case SAVE_DEVICE:

            return {
                ...state,
                saveLoading: true
            };

        case SAVE_DEVICE_SUCCESS:

            return {
                ...state,
                visibleModal: false,
                saveLoading:  false
            };

        case SAVE_DEVICE_FAILED:

            return {
                ...state,
                saveLoading: false,
                error:       action.payload
            };

        default:
            return state;
    }
};