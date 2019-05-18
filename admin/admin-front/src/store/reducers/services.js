import { identity, indexBy, keys } from 'ramda';

import { createAction } from "../../utils/action";
import { excludeById } from "../../utils/common";


export const LOAD_SERVICES         = 'admin/service/LOAD_SERVICES';
export const LOAD_SERVICES_SUCCESS = 'admin/service/LOAD_SERVICES_SUCCESS';
export const LOAD_SERVICES_FAILED  = 'admin/service/LOAD_SERVICES_FAILED';

export const OPEN_EDIT_SERVICE_MODAL = 'admin/service/OPEN_EDIT_SERVICE_MODAL';
export const CANCEL_EDIT_SERVICE     = 'admin/service/CANCEL_EDIT_SERVICE';
export const OPEN_CREATE_SERVICE     = 'admin/service/OPEN_CREATE_SERVICE';

export const CHANGE_SERVICE = 'admin/service/CHANGE_SERVICE';

export const SAVE_SERVICE             = 'admin/service/SAVE_SERVICE';
export const SAVE_SERVICE_SUCCESS     = 'admin/service/SAVE_SERVICE_SUCCESS';
export const SAVE_NEW_SERVICE_SUCCESS = 'admin/service/SAVE_NEW_SERVICE_SUCCESS';
export const SAVE_SERVICE_FAILED      = 'admin/service/SAVE_SERVICE_FAILED';

export const DELETE_SERVICE         = 'admin/service/DELETE_SERVICE';
export const DELETE_SERVICE_SUCCESS = 'admin/service/DELETE_SERVICE_SUCCESS';
export const DELETE_SERVICE_FAILED  = 'admin/service/DELETE_SERVICE_FAILED';


export const openEditServiceModal = createAction(OPEN_EDIT_SERVICE_MODAL);
export const loadServices         = createAction(LOAD_SERVICES);
export const saveService          = createAction(SAVE_SERVICE);
export const deleteService        = createAction(DELETE_SERVICE);
export const cancelEditService    = createAction(CANCEL_EDIT_SERVICE);
export const changeService        = createAction(CHANGE_SERVICE);

const initState = {
    list:     [],
    editable: {}
};


export const SERVICE_TYPE_LABELS = {
    CAFETERIA:      'Кафетерий',
    LIBRARY:        'Библиотека',
    TYPOGRAPHY:     'Типография',
    MEDICAL_CENTER: 'Медицинское отделение',
    COMPUTER_CLASS: 'Компьютерный класс'
};

export const SERVICE_TYPES = indexBy(identity, keys(SERVICE_TYPE_LABELS));

export const services = (state = initState, action = {}) => {

    switch (action.type) {

        case DELETE_SERVICE_SUCCESS:

            return {
                ...state,
                list: excludeById(action.payload, state.list)
            };

        case OPEN_CREATE_SERVICE:

            return {
                ...state,
                editable: {
                    isNew: true
                }
            };

        case OPEN_EDIT_SERVICE_MODAL:

            return {
                ...state,
                editable: action.payload
            };

        case LOAD_SERVICES_SUCCESS:

            return {
                ...state,
                list: action.payload
            };
        case CANCEL_EDIT_SERVICE:

            return {
                ...state,
                editable: {}
            };
        case SAVE_NEW_SERVICE_SUCCESS:

            return {
                ...state,
                editable: {},
                list:     [
                    ...state.list,
                    action.payload
                ]
            };
        case SAVE_SERVICE_SUCCESS:

            return {
                ...state,
                editable: {},
                list:     [
                    ...state.list.map(service => {
                        if (service.id === action.payload.id) {
                            return action.payload;
                        }
                        return service;
                    }),
                ]
            };
        case CHANGE_SERVICE:

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
