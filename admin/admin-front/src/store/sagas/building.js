import { takeLatest, put, call } from "redux-saga/effects";
import { message } from "antd";

import { getBuildings } from "../../api";
import { LOAD_BUILDINGS, LOAD_BUILDINGS_SUCCESS } from "../reducers/buildings";
import { LOAD_SERVICES_FAILED } from "../reducers/services";


export default function* main() {
    yield takeLatest(LOAD_BUILDINGS, fetchBuilding);
}

function* fetchBuilding() {
    try {
        const list = yield call(getBuildings);

        yield put({ type: LOAD_BUILDINGS_SUCCESS, payload: list })
    } catch (error) {
        message.error('Ошибка загрузки зданий.');
        yield put({ type: LOAD_SERVICES_FAILED, payload: error })
    }
}
