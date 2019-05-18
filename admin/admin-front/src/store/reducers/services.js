import { identity, indexBy, keys, prop } from 'ramda';

import { createAction } from "../../utils/action";

export const LOAD_SERVICES         = 'admin/device/LOAD_SERVICES';
export const LOAD_SERVICES_SUCCESS = 'admin/device/LOAD_SERVICES_SUCCESS';
export const LOAD_SERVICES_FAILED  = 'admin/device/LOAD_SERVICES_FAILED';

export const loadServices = createAction(LOAD_SERVICES);

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


        case LOAD_SERVICES_SUCCESS:

            return {
                ...state,
                list: action.payload
            };

        default:
            return state;
    }
};
