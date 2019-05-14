import { prop, map, find, propEq } from "ramda";

import { calculateCentroid } from "../../utils/map";
import { createAction } from "../../utils/action";


export const LOAD         = 'admin/map/LOAD';
export const LOAD_SUCCESS = 'admin/map/LOAD_SUCCESS';
export const LOAD_FAILED  = 'admin/map/LOAD_FAILED';

export const UNDO_CREATE_POINT = 'admin/map/UNDO_CREATE_POINT';
export const REDO_CREATE_POINT = 'admin/map/REDO_CREATE_POINT';


export const CREATE_POINT                = 'admin/map/CREATE_POINT';
export const SAVE_CREATED_POINTS         = 'admin/map/SAVE_CREATED_POINTS';
export const CANCEL_CREATED_POINTS       = 'admin/map/CANCEL_CREATED_POINTS';
export const SAVE_CREATED_POINTS_SUCCESS = 'admin/map/SAVE_CREATED_POINTS_SUCCESS';
export const SAVE_CREATED_POINTS_FAILED  = 'admin/map/SAVE_CREATED_POINTS_FAILED';

export const ADD_EDGE                   = 'admin/map/ADD_EDGE';
export const SAVE_CREATED_EDGES         = 'admin/map/SAVE_CREATED_EDGES';
export const CANCEL_CREATED_EDGES       = 'admin/map/CANCEL_CREATED_EDGES';
export const SAVE_CREATED_EDGES_SUCCESS = 'admin/map/SAVE_CREATED_EDGES_SUCCESS';
export const SAVE_CREATED_EDGES_FAILED  = 'admin/map/SAVE_CREATED_EDGES_FAILED';

export const loadBuildingMap     = createAction(LOAD);
export const createPoint         = createAction(CREATE_POINT);
export const undoCreatePoint     = createAction(UNDO_CREATE_POINT);
export const redoCreatePoint     = createAction(REDO_CREATE_POINT);
export const saveCreatedPoints   = createAction(SAVE_CREATED_POINTS);
export const cancelCreatedPoints = createAction(CANCEL_CREATED_POINTS);
export const addEdge             = createAction(ADD_EDGE);
export const saveCreatedEdges    = createAction(SAVE_CREATED_EDGES);
export const cancelCreatedEdges  = createAction(CANCEL_CREATED_EDGES);

const initialState = {
    loading:          false,
    error:            null,
    data:             {
        schemes:  [],
        points:   [],
        elements: [],
        edges:    []
    },
    buildingSchemeId: 3
};

const reducer = (state = initialState, action = {}) => {
    switch (action.type) {

        case SAVE_CREATED_EDGES_SUCCESS:

            return {
                ...state,
                loading: false,
                data:    {
                    ...state.data,
                    edges: [
                        ...state.data.edges,
                        ...map(edge => [ edge.leftPointId, edge.rightPointId ], action.payload)
                    ]
                }
            };

        case SAVE_CREATED_POINTS_SUCCESS:
            const getFloor = getFloorByScheme(state.data.schemes);

            return {
                ...state,
                loading: false,
                data:    {
                    ...state.data,
                    points: [
                        ...state.data.points,
                        ...map(mapPoint(getFloor), action.payload)
                    ]
                }
            };

        case LOAD:
        case SAVE_CREATED_POINTS:
            return {
                ...state,
                loading: true
            };

        case LOAD_SUCCESS:

            return {
                ...state,
                data:    mapFromServer(action.payload),
                loading: false
            };

        case LOAD_FAILED:
        case SAVE_CREATED_POINTS_FAILED:

            return {
                ...state,
                error:   action.payload,
                loading: false
            };

        default:
            return state
    }
};

const mapFromServer = data => {
    const { points, edges, elements, schemes } = data;

    const getFloor = getFloorByScheme(schemes);

    return {
        points:   map(mapPoint(getFloor), points),
        elements: map(mapElement(getFloor), elements),
        edges:    map(mapEdge, edges),
        schemes:  schemes || []
    }
};

const mapPoint = getFloor => point => {
    const { buildingSchemeId, x, y, id } = point;

    const floor = getFloor(buildingSchemeId);

    return {
        x,
        y,
        id,
        floor,
        buildingSchemeId
    }
};


const mapElement = getFloor => element => {
    const { coordinates, id, label, type, buildingSchemeId, pointId } = element;

    const floor        = getFloor(buildingSchemeId);
    const textCentroid = calculateCentroid(coordinates);

    return {
        id,
        type,
        coordinates,
        label,
        pointId,
        floor,
        textCentroid,
        buildingSchemeId
    }
};

const getFloorByScheme = schemes => schemeId => prop('floor')(find(propEq('id', schemeId))(schemes));

const mapEdge = edge => {
    const { leftPointId, rightPointId } = edge;

    return [
        leftPointId,
        rightPointId
    ]
};


export const MAP_ELEMENTS_TYPES = {
    ROOM:     'ROOM',
    CORRIDOR: 'CORRIDOR',
    DOOR:     'DOOR',
    STAIRS:   'STAIRS'
};
export default reducer;