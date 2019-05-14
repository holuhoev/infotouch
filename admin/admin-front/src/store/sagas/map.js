import { takeLatest, put, call, select } from "redux-saga/effects";

import {
    LOAD,
    LOAD_SUCCESS,
    LOAD_FAILED,
    SAVE_CREATED_POINTS,
    SAVE_CREATED_POINTS_FAILED,
    SAVE_CREATED_POINTS_SUCCESS, SAVE_CREATED_EDGES_SUCCESS, SAVE_CREATED_EDGES_FAILED, SAVE_CREATED_EDGES
} from "../reducers/map";
import { createNewEdges, createNewPoints, getBuildingMap } from "../../api";
import {
    selectMapCurrentSchemeId,
    selectRoomId,
    selectSchemeCreatedPoints,
    selectSchemeEdgesForSave
} from "../selectors/map";


export default function* main() {
    yield takeLatest(LOAD, fetchBuildingMap);
    yield takeLatest(SAVE_CREATED_POINTS, saveCreatedPoints);
    yield takeLatest(SAVE_CREATED_EDGES, saveCreatedEdges)
}

function* fetchBuildingMap(action) {
    try {
        const buildingId  = action.payload;
        const buildingMap = yield call(getBuildingMap, buildingId);

        yield put({ type: LOAD_SUCCESS, payload: buildingMap })
    } catch (error) {
        yield put({ type: LOAD_FAILED, payload: error })
    }
}

function* saveCreatedPoints() {
    try {
        const state  = yield select();
        const points = selectSchemeCreatedPoints(state);

        const data = {
            points:           points.map(point => ({
                ...point,
                schemeElementId: selectRoomId(state, [ point.x, point.y ])
            })),
            buildingSchemeId: selectMapCurrentSchemeId(state)
        };

        const saved = yield call(createNewPoints, data);
        yield put({ type: SAVE_CREATED_POINTS_SUCCESS, payload: saved })
    } catch (error) {
        yield put({ type: SAVE_CREATED_POINTS_FAILED, payload: error })
    }
}

function* saveCreatedEdges() {
    try {
        const state = yield select();
        const data  = {
            edges: selectSchemeEdgesForSave(state)
        };

        const saved = yield call(createNewEdges, data);
        yield put({ type: SAVE_CREATED_EDGES_SUCCESS, payload: saved })
    } catch (error) {
        yield put({ type: SAVE_CREATED_EDGES_FAILED, payload: error })
    }
}