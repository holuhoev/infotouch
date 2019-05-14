import undoable, { includeAction } from 'redux-undo'


import {
    SAVE_CREATED_EDGES_SUCCESS,
    ADD_EDGE,
    CANCEL_CREATED_POINTS, UNDO, REDO
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

const undoableEdges = undoable(reducer, {
    filter:   includeAction(ADD_EDGE),
    undoType: UNDO,
    redoType: REDO
});

export default undoableEdges;