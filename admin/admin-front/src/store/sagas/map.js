import { takeLatest, put, call } from "redux-saga/effects";

import {
    LOAD,
    LOAD_SUCCESS,
    LOAD_FAILED
} from "../reducers/map";
import { getBuildingMap } from "../../api";


export default function* main() {
    yield takeLatest(LOAD, fetchBuildingMap)
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