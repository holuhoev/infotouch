import undoable, { includeAction } from 'redux-undo'

import {
    CANCEL_CREATED_POINTS,
    CREATE_POINT,
    REDO_CREATE_POINT,
    SAVE_CREATED_POINTS_SUCCESS,
    UNDO_CREATE_POINT
} from "./map";


const reducer = (state = [], action = {}) => {
    switch (action.type) {
        case SAVE_CREATED_POINTS_SUCCESS:
        case CANCEL_CREATED_POINTS:

            return [];

        case CREATE_POINT:

            return [
                ...state,
                [action.payload.x, action.payload.y]
            ];
        default:
            return state;
    }
};

const undoablePoints = undoable(reducer, {
    filter:   includeAction(CREATE_POINT),
    undoType: UNDO_CREATE_POINT,
    redoType: REDO_CREATE_POINT
});

export default undoablePoints;