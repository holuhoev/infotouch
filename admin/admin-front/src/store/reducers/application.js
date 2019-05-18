import {
    CANCEL_EDIT_DEVICE,
    LOAD_DEVICES,
    LOAD_DEVICES_FAILED,
    LOAD_DEVICES_SUCCESS,
    LOAD_ONE_DEVICE,
    LOAD_ONE_DEVICE_FAILED,
    LOAD_ONE_DEVICE_SUCCESS,
    OPEN_CREATE_DEVICE,
    SAVE_DEVICE,
    SAVE_DEVICE_FAILED,
    SAVE_DEVICE_SUCCESS,
    SAVE_NEW_DEVICE_SUCCESS,
} from "./devices";
import {
    CANCEL_EDIT_SERVICE,
    LOAD_SERVICES,
    LOAD_SERVICES_FAILED,
    LOAD_SERVICES_SUCCESS,
    OPEN_CREATE_SERVICE,
    OPEN_EDIT_SERVICE_MODAL,
    SAVE_NEW_SERVICE_SUCCESS,
    SAVE_SERVICE, SAVE_SERVICE_FAILED, SAVE_SERVICE_SUCCESS
} from "./services";
import { LOAD_BUILDINGS, LOAD_BUILDINGS_FAILED, LOAD_BUILDINGS_SUCCESS, SELECT_BUILDING } from "./buildings";

const initialState = {
    listLoading:        false,
    servicesLoading:    false,
    buildingsLoading:   false,
    oneLoading:         false,
    error:              null,
    visibleModal:       false,
    saveLoading:        false,
    selectedBuildingId: undefined
};

export const application = (state = initialState, action = {}) => {
    switch (action.type) {

        case LOAD_BUILDINGS:

            return {
                ...state,
                buildingsLoading: true
            };
        case LOAD_BUILDINGS_SUCCESS:

            return {
                ...state,
                buildingsLoading: false
            };

        case LOAD_BUILDINGS_FAILED:

            return {
                ...state,
                buildingsLoading: false,
                error:            action.payload
            };

        case SELECT_BUILDING:

            return {
                ...state,
                selectedBuildingId: action.payload
            };

        case OPEN_CREATE_DEVICE:
        case OPEN_EDIT_SERVICE_MODAL:
        case OPEN_CREATE_SERVICE:

            return {
                ...state,
                visibleModal: true
            };

        case LOAD_DEVICES:
            return {
                ...state,
                listLoading: true
            };
        case LOAD_SERVICES:
            return {
                ...state,
                servicesLoading: true
            };

        case LOAD_SERVICES_SUCCESS:

            return {
                ...state,
                servicesLoading: false
            };

        case LOAD_SERVICES_FAILED:

            return {
                ...state,
                servicesLoading: false,
                error:           action.payload
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
        case CANCEL_EDIT_SERVICE:
            return {
                ...state,
                visibleModal: false
            };

        case SAVE_DEVICE:
        case SAVE_SERVICE:
            return {
                ...state,
                saveLoading: true
            };

        case SAVE_DEVICE_SUCCESS:
        case SAVE_NEW_DEVICE_SUCCESS:
        case SAVE_SERVICE_SUCCESS:
        case SAVE_NEW_SERVICE_SUCCESS:

            return {
                ...state,
                visibleModal: false,
                saveLoading:  false
            };

        case SAVE_DEVICE_FAILED:
        case SAVE_SERVICE_FAILED:
            return {
                ...state,
                saveLoading: false,
                error:       action.payload
            };

        default:
            return state;
    }
};