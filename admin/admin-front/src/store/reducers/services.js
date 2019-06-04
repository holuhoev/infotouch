import { filter, identity, indexBy, keys } from 'ramda';

import { createAction } from "../../utils/action";
import { excludeById } from "../../utils/common";
import { DELETE_POINT_SUCCESS } from "./map";


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

export const CHANGE_SERVICE_POINT         = 'admin/service/CHANGE_SERVICE_POINT';
export const REMOVE_SERVICE_POINT         = 'admin/service/REMOVE_SERVICE_POINT';
export const CHANGE_SERVICE_POINT_SUCCESS = 'admin/service/CHANGE_SERVICE_POINT_SUCCESS';
export const CHANGE_SERVICE_POINT_FAILED  = 'admin/service/CHANGE_SERVICE_POINT_FAILED';

export const openEditServiceModal   = createAction(OPEN_EDIT_SERVICE_MODAL);
export const loadServices           = createAction(LOAD_SERVICES);
export const saveService            = createAction(SAVE_SERVICE);
export const deleteService          = createAction(DELETE_SERVICE);
export const cancelEditService      = createAction(CANCEL_EDIT_SERVICE);
export const changeService          = createAction(CHANGE_SERVICE);
export const openCreateServiceModal = createAction(OPEN_CREATE_SERVICE);
export const changeServicePoint     = createAction(CHANGE_SERVICE_POINT);
export const removeServicePoint     = createAction(REMOVE_SERVICE_POINT);

const initState = {
    list:     [],
    editable: {},
};


export const SERVICE_TYPE_LABELS = {
    CAFETERIA:      'Кафетерий',
    LIBRARY:        'Библиотека',
    TYPOGRAPHY:     'Типография',
    MEDICAL_CENTER: 'Медицинское отделение',
    ATM:            'Банкомат',
    READING_ROOM:   'Читальный зал',
    COOLER:         'Кулер с водой',
    COMPUTER_CLASS: 'Компьютерный класс',
    PRINTER:        'Принтер',
    TOILET:         'Туалет',
    OTHER:          'Другое'
};

export const SERVICE_TYPES = indexBy(identity, keys(SERVICE_TYPE_LABELS));

const service = (state = {}, action = {}) => {
    switch (action.type) {

        case DELETE_POINT_SUCCESS:
            let id = action.payload;

            return {
                ...state,
                list: filter(({ pointId }) => pointId !== id, state.list)
            };

        case REMOVE_SERVICE_POINT:
            if (action.payload === state.pointId) {

                return {
                    ...state,
                    pointId: null
                }
            }

            return state;

        case CHANGE_SERVICE_POINT:

            if (action.payload.pointId === state.pointId) {

                return {
                    ...state,
                    pointId: null
                }
            }

            if (action.payload.serviceId !== state.id.toString()) {
                return state;
            }

            return {
                ...state,
                pointId: action.payload.pointId
            };

        default:
            return state;
    }
};


export const services = (state = initState, action = {}) => {

    switch (action.type) {

        case CHANGE_SERVICE_POINT:
        case REMOVE_SERVICE_POINT:
            return {
                ...state,
                list: state.list.map(s => service(s, action))
            };

        case DELETE_SERVICE_SUCCESS:

            return {
                ...state,
                list: excludeById(action.payload, state.list)
            };

        case OPEN_CREATE_SERVICE:

            return {
                ...state,
                editable: {
                    isNew: true,
                    type:  SERVICE_TYPES.OTHER
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
