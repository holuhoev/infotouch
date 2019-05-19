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
    SAVE_NEW_DEVICE_SUCCESS, SELECT_DEVICE,
} from "./devices";
import {
    CANCEL_EDIT_SERVICE,
    LOAD_SERVICES,
    LOAD_SERVICES_FAILED,
    LOAD_SERVICES_SUCCESS,
    OPEN_CREATE_SERVICE,
    OPEN_EDIT_SERVICE_MODAL,
    SAVE_NEW_SERVICE_SUCCESS,
    SAVE_SERVICE,
    SAVE_SERVICE_FAILED,
    SAVE_SERVICE_SUCCESS
} from "./services";
import { LOAD_BUILDINGS, LOAD_BUILDINGS_FAILED, LOAD_BUILDINGS_SUCCESS, SELECT_BUILDING } from "./buildings";
import {
    CANCEL_EDIT_UNIT,
    LOAD_UNITS,
    LOAD_UNITS_FAILED,
    LOAD_UNITS_SUCCESS,
    OPEN_CREATE_UNIT,
    OPEN_EDIT_UNIT_MODAL,
    SAVE_NEW_UNIT_SUCCESS,
    SAVE_UNIT,
    SAVE_UNIT_FAILED,
    SAVE_UNIT_SUCCESS
} from "./units";
import {
    CANCEL_EDIT_EVENT,
    LOAD_EVENTS,
    LOAD_EVENTS_FAILED,
    LOAD_EVENTS_SUCCESS,
    OPEN_CREATE_EVENT,
    OPEN_EDIT_EVENT_MODAL, SAVE_EVENT, SAVE_EVENT_FAILED, SAVE_EVENT_SUCCESS, SAVE_NEW_EVENT_SUCCESS
} from "./events";

const initialState = {
    listLoading:        false,
    servicesLoading:    false,
    buildingsLoading:   false,
    oneLoading:         false,
    error:              null,
    visibleModal:       false,
    saveLoading:        false,
    selectedBuildingId: undefined,
    selectedDeviceId:   undefined,
    eventsLoading:      false
};

export const application = (state = initialState, action = {}) => {
    switch (action.type) {

        case SELECT_DEVICE:

            return {
                ...state,
                selectedDeviceId: action.payload
            };

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
        case OPEN_CREATE_UNIT:
        case OPEN_EDIT_UNIT_MODAL:
        case OPEN_CREATE_EVENT:
        case OPEN_EDIT_EVENT_MODAL:
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

        case LOAD_UNITS:

            return {
                ...state,
                unitsLoading: true
            };
        case LOAD_UNITS_SUCCESS:

            return {
                ...state,
                unitsLoading: false
            };

        case LOAD_UNITS_FAILED:

            return {
                ...state,
                unitsLoading: false,
                error:        action.payload
            };

        case LOAD_EVENTS:

            return {
                ...state,
                eventsLoading: true
            };
        case LOAD_EVENTS_SUCCESS:

            return {
                ...state,
                eventsLoading: false
            };

        case LOAD_EVENTS_FAILED:

            return {
                ...state,
                eventsLoading: false,
                error:         action.payload
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
        case CANCEL_EDIT_UNIT:
        case CANCEL_EDIT_EVENT:
            return {
                ...state,
                visibleModal: false
            };

        case SAVE_DEVICE:
        case SAVE_SERVICE:
        case SAVE_UNIT:
        case SAVE_EVENT:
            return {
                ...state,
                saveLoading: true
            };

        case SAVE_DEVICE_SUCCESS:
        case SAVE_NEW_DEVICE_SUCCESS:
        case SAVE_SERVICE_SUCCESS:
        case SAVE_NEW_SERVICE_SUCCESS:
        case SAVE_UNIT_SUCCESS:
        case SAVE_NEW_UNIT_SUCCESS:
        case SAVE_EVENT_SUCCESS:
        case SAVE_NEW_EVENT_SUCCESS:
            return {
                ...state,
                visibleModal: false,
                saveLoading:  false
            };

        case SAVE_DEVICE_FAILED:
        case SAVE_SERVICE_FAILED:
        case SAVE_UNIT_FAILED:
        case SAVE_EVENT_FAILED:
            return {
                ...state,
                saveLoading: false,
                error:       action.payload
            };

        default:
            return state;
    }
};