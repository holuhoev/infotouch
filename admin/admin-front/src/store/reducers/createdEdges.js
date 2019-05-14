import undoable, { includeAction } from 'redux-undo'


import {
    SAVE_CREATED_EDGES_SUCCESS,
    ADD_EDGE,
    CANCEL_CREATED_POINTS
} from "./map";


const reducer = (state = [], action = {}) => {
    switch (action.type) {
        case SAVE_CREATED_EDGES_SUCCESS:
        case CANCEL_CREATED_POINTS:

            return [];

        case ADD_EDGE:

            return [
                ...state,
                action.payload
            ];
        default:
            return state;
    }
};
//
// const undoablePoints = undoable(reducer, {
//     filter:   includeAction(CREATE_POINT),
//     undoType: UNDO_CREATE_POINT,
//     redoType: REDO_CREATE_POINT
// });

export default reducer;