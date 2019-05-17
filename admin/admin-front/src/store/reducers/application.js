import { LOAD_DEVICES_FAILED, LOAD_DEVICES_SUCCESS } from "./devices";

const initialState = {
    loading: false,
    error:   null
};

export const application = (state = initialState, action = {}) => {
    switch (action.type) {

        case LOAD_DEVICES_SUCCESS:

            return {
                ...state,
                loading: false
            };

        case LOAD_DEVICES_FAILED:

            return {
                ...state,
                loading: false,
                error:   action.payload
            };

        default:
            return state;
    }
};