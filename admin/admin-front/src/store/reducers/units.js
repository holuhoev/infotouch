import { createAction } from "../../utils/action";
import { excludeById } from "../../utils/common";


export const LOAD_UNITS         = 'admin/unit/LOAD_UNITS';
export const LOAD_UNITS_SUCCESS = 'admin/unit/LOAD_UNITS_SUCCESS';
export const LOAD_UNITS_FAILED  = 'admin/unit/LOAD_UNITS_FAILED';

export const OPEN_EDIT_UNIT_MODAL = 'admin/unit/OPEN_EDIT_UNIT_MODAL';
export const CANCEL_EDIT_UNIT     = 'admin/unit/CANCEL_EDIT_UNIT';
export const OPEN_CREATE_UNIT     = 'admin/unit/OPEN_CREATE_UNIT';

export const CHANGE_UNIT = 'admin/unit/CHANGE_UNIT';

export const SAVE_UNIT             = 'admin/unit/SAVE_UNIT';
export const SAVE_UNIT_SUCCESS     = 'admin/unit/SAVE_UNIT_SUCCESS';
export const SAVE_NEW_UNIT_SUCCESS = 'admin/unit/SAVE_NEW_UNIT_SUCCESS';
export const SAVE_UNIT_FAILED      = 'admin/unit/SAVE_UNIT_FAILED';

export const DELETE_UNIT         = 'admin/unit/DELETE_UNIT';
export const DELETE_UNIT_SUCCESS = 'admin/unit/DELETE_UNIT_SUCCESS';
export const DELETE_UNIT_FAILED  = 'admin/unit/DELETE_UNIT_FAILED';


export const openEditUnitModal   = createAction(OPEN_EDIT_UNIT_MODAL);
export const loadUnits           = createAction(LOAD_UNITS);
export const saveUnit            = createAction(SAVE_UNIT);
export const deleteUnit          = createAction(DELETE_UNIT);
export const cancelEditUnit      = createAction(CANCEL_EDIT_UNIT);
export const changeUnit          = createAction(CHANGE_UNIT);
export const openCreateUnitModal = createAction(OPEN_CREATE_UNIT);

const initState = {
    list:     [],
    editable: {},
};



export const units = (state = initState, action = {}) => {

    switch (action.type) {

        case DELETE_UNIT_SUCCESS:

            return {
                ...state,
                list: excludeById(action.payload, state.list)
            };

        case OPEN_CREATE_UNIT:

            return {
                ...state,
                editable: {
                    isNew: true
                }
            };

        case OPEN_EDIT_UNIT_MODAL:

            return {
                ...state,
                editable: action.payload
            };

        case LOAD_UNITS_SUCCESS:

            return {
                ...state,
                list: action.payload
            };
        case CANCEL_EDIT_UNIT:

            return {
                ...state,
                editable: {}
            };
        case SAVE_NEW_UNIT_SUCCESS:

            return {
                ...state,
                editable: {},
                list:     [
                    ...state.list,
                    action.payload
                ]
            };
        case SAVE_UNIT_SUCCESS:

            return {
                ...state,
                editable: {},
                list:     [
                    ...state.list.map(unit => {
                        if (unit.id === action.payload.id) {
                            return action.payload;
                        }
                        return unit;
                    }),
                ]
            };
        case CHANGE_UNIT:

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
