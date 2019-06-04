import { prop, map, find, propEq, isEmpty, indexBy, filter } from "ramda";

import { calculateCentroid, calculateStairsLines } from "../../utils/map";
import { createAction } from "../../utils/action";
import { excludeById } from "../../utils/common";

export const CHANGE_SCHEME = 'admin/map/CHANGE_SCHEME';

export const LOAD         = 'admin/map/LOAD';
export const LOAD_SUCCESS = 'admin/map/LOAD_SUCCESS';
export const LOAD_FAILED  = 'admin/map/LOAD_FAILED';

export const UNDO = 'admin/map/UNDO';
export const REDO = 'admin/map/REDO';


export const CREATE_POINT                = 'admin/map/CREATE_POINT';
export const SAVE_CREATED_POINTS         = 'admin/map/SAVE_CREATED_POINTS';
export const CANCEL_CREATED_POINTS       = 'admin/map/CANCEL_CREATED_POINTS';
export const SAVE_CREATED_POINTS_SUCCESS = 'admin/map/SAVE_CREATED_POINTS_SUCCESS';
export const SAVE_CREATED_POINTS_FAILED  = 'admin/map/SAVE_CREATED_POINTS_FAILED';

export const DELETE_POINT         = 'admin/map/DELETE_POINT';
export const DELETE_POINT_SUCCESS = 'admin/map/DELETE_POINT_SUCCESS';
export const DELETE_POINT_FAILED  = 'admin/map/DELETE_POINT_FAILED';

export const ADD_EDGE                   = 'admin/map/ADD_EDGE';
export const SAVE_CREATED_EDGES         = 'admin/map/SAVE_CREATED_EDGES';
export const CANCEL_CREATED_EDGES       = 'admin/map/CANCEL_CREATED_EDGES';
export const SAVE_CREATED_EDGES_SUCCESS = 'admin/map/SAVE_CREATED_EDGES_SUCCESS';
export const SAVE_CREATED_EDGES_FAILED  = 'admin/map/SAVE_CREATED_EDGES_FAILED';

export const CHANGE_SELECTED_POINT   = 'admin/map/CHANGE_SELECTED_POINT';
export const CHANGE_SELECTED_ELEMENT = 'admin/map/CHANGE_SELECTED_ELEMENT';

export const TOGGLE_EDIT_BUTTON = 'admin/map/TOGGLE_EDIT_BUTTON';

export const loadBuildingMap       = createAction(LOAD);
export const createPoint           = createAction(CREATE_POINT);
export const undo                  = createAction(UNDO);
export const redo                  = createAction(REDO);
export const saveCreatedPoints     = createAction(SAVE_CREATED_POINTS);
export const cancelCreatedPoints   = createAction(CANCEL_CREATED_POINTS);
export const addEdge               = createAction(ADD_EDGE);
export const saveCreatedEdges      = createAction(SAVE_CREATED_EDGES);
export const cancelCreatedEdges    = createAction(CANCEL_CREATED_EDGES);
export const changeScheme          = createAction(CHANGE_SCHEME);
export const changeSelectedPoint   = createAction(CHANGE_SELECTED_POINT);
export const changeSelectedElement = createAction(CHANGE_SELECTED_ELEMENT);
export const deletePoint           = createAction(DELETE_POINT);
export const toggleEditButton      = createAction(TOGGLE_EDIT_BUTTON);

const initialState = {
    loading:           false,
    error:             null,
    data:              {
        schemes:  [],
        points:   {},
        elements: [],
        edges:    []
    },
    isEdit:            false,
    buildingSchemeId:  null,
    selectedPointId:   null,
    selectedElementId: null
};

const reducer = (state = initialState, action = {}) => {
    switch (action.type) {
        case TOGGLE_EDIT_BUTTON:

            return {
                ...state,
                isEdit: !state.isEdit
            };

        case DELETE_POINT_SUCCESS:
            let id = action.payload;

            return {
                ...state,
                selectedPointId: null,
                data:            {
                    ...state.data,
                    points: excludeById(id, state.data.points),
                    edges:  filter(([ p1, p2 ]) => {
                        return p1 !== id && p2 !== id
                    }, state.data.edges)
                }
            };

        case CHANGE_SELECTED_ELEMENT:

            return {
                ...state,
                selectedElementId: action.payload,
                selectedPointId:   null
            };

        case CHANGE_SELECTED_POINT:

            return {
                ...state,
                selectedPointId:   action.payload,
                selectedElementId: null
            };

        case CHANGE_SCHEME:

            return {
                ...state,
                buildingSchemeId: action.payload
            };

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

            return {
                ...state,
                loading: false,
                data:    {
                    ...state.data,
                    points: {
                        ...state.data.points,
                        ...indexBy(prop('id'), action.payload)
                    }
                }
            };

        case LOAD:
        case SAVE_CREATED_POINTS:
            return {
                ...state,
                loading: true,
                error:   null
            };

        case LOAD_SUCCESS:
            const data        = mapFromServer(action.payload);
            const { schemes } = data;

            return {
                ...state,
                data:             data,
                loading:          false,
                error:            null,
                buildingSchemeId: isEmpty(schemes) ? null : schemes[ 0 ].id
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
        points:   indexBy(prop('id'), points),
        elements: indexBy(prop('id'), map(mapElement(getFloor), elements)),
        edges:    map(mapEdge, edges),
        schemes:  schemes || []
    }
};


const mapElement = getFloor => element => {
    const { coordinates, id, label, type, buildingSchemeId, pointId } = element;

    const floor        = getFloor(buildingSchemeId);
    const textCentroid = calculateCentroid(coordinates);
    const stairLines   = getStairLines(element);

    return {
        id,
        type,
        coordinates,
        label,
        pointId,
        floor,
        textCentroid,
        buildingSchemeId,
        lines: stairLines
    }
};

const getStairLines = element => {
    if (!isElementIsStair(element))
        return [];

    return calculateStairsLines(element.coordinates)
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

export const isElementHasLabel = element => {

    return element.type === MAP_ELEMENTS_TYPES.ROOM
        && !!element.textCentroid
        && !!element.label;
};

export const isElementIsStair = element => element.type === MAP_ELEMENTS_TYPES.STAIRS;

export const isRoomOrCorridor = element =>
    element.type === MAP_ELEMENTS_TYPES.ROOM ||
    element.type === MAP_ELEMENTS_TYPES.CORRIDOR;


export default reducer;