import { createAction } from "../../utils/action";
import { excludeById } from "../../utils/common";


export const LOAD_EVENTS         = 'admin/event/LOAD_EVENTS';
export const LOAD_EVENTS_SUCCESS = 'admin/event/LOAD_EVENTS_SUCCESS';
export const LOAD_EVENTS_FAILED  = 'admin/event/LOAD_EVENTS_FAILED';

export const OPEN_EDIT_EVENT_MODAL = 'admin/event/OPEN_EDIT_EVENT_MODAL';
export const CANCEL_EDIT_EVENT     = 'admin/event/CANCEL_EDIT_EVENT';
export const OPEN_CREATE_EVENT     = 'admin/event/OPEN_CREATE_EVENT';

export const CHANGE_EVENT = 'admin/event/CHANGE_EVENT';

export const SAVE_EVENT             = 'admin/event/SAVE_EVENT';
export const SAVE_EVENT_SUCCESS     = 'admin/event/SAVE_EVENT_SUCCESS';
export const SAVE_NEW_EVENT_SUCCESS = 'admin/event/SAVE_NEW_EVENT_SUCCESS';
export const SAVE_EVENT_FAILED      = 'admin/event/SAVE_EVENT_FAILED';

export const DELETE_EVENT         = 'admin/event/DELETE_EVENT';
export const DELETE_EVENT_SUCCESS = 'admin/event/DELETE_EVENT_SUCCESS';
export const DELETE_EVENT_FAILED  = 'admin/event/DELETE_EVENT_FAILED';


export const openEditEventModal   = createAction(OPEN_EDIT_EVENT_MODAL);
export const loadEvents           = createAction(LOAD_EVENTS);
export const saveEvent            = createAction(SAVE_EVENT);
export const deleteEvent          = createAction(DELETE_EVENT);
export const cancelEditEvent      = createAction(CANCEL_EDIT_EVENT);
export const changeEvent          = createAction(CHANGE_EVENT);
export const openCreateEventModal = createAction(OPEN_CREATE_EVENT);

const initState = {
    list:     [],
    editable: {},
};



export const events = (state = initState, action = {}) => {

    switch (action.type) {

        case DELETE_EVENT_SUCCESS:

            return {
                ...state,
                list: excludeById(action.payload, state.list)
            };

        case OPEN_CREATE_EVENT:

            return {
                ...state,
                editable: {
                    isNew: true
                }
            };

        case OPEN_EDIT_EVENT_MODAL:

            return {
                ...state,
                editable: action.payload
            };

        case LOAD_EVENTS_SUCCESS:

            return {
                ...state,
                list: action.payload
            };
        case CANCEL_EDIT_EVENT:

            return {
                ...state,
                editable: {}
            };
        case SAVE_NEW_EVENT_SUCCESS:

            return {
                ...state,
                editable: {},
                list:     [
                    ...state.list,
                    action.payload
                ]
            };
        case SAVE_EVENT_SUCCESS:

            return {
                ...state,
                editable: {},
                list:     [
                    ...state.list.map(event => {
                        if (event.id === action.payload.id) {
                            return action.payload;
                        }
                        return event;
                    }),
                ]
            };
        case CHANGE_EVENT:

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
