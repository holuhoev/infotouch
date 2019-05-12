import { takeLatest, put, call, select } from "redux-saga/effects";

import {
    LOAD,
    LOAD_SUCCESS,
    LOAD_FAILED,
    SAVE_CREATED_POINTS,
    SAVE_CREATED_POINTS_FAILED,
    SAVE_CREATED_POINTS_SUCCESS
} from "../reducers/map";
import { createNewPoints, getBuildingMap } from "../../api";
import { selectMapCurrentSchemeId, selectSchemeCreatedPoints } from "../selectors/map";


export default function* main() {
    yield takeLatest(LOAD, fetchBuildingMap);
    yield takeLatest(SAVE_CREATED_POINTS, saveCreatedPoints)
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
        const state = yield select();
        const data  = {
            points:           selectSchemeCreatedPoints(state),
            buildingSchemeId: selectMapCurrentSchemeId(state)
        };

        const saved = yield call(createNewPoints, data);
        yield put({ type: SAVE_CREATED_POINTS_SUCCESS, payload: saved })
    } catch (error) {
        yield put({ type: SAVE_CREATED_POINTS_FAILED, payload: error })
    }
}