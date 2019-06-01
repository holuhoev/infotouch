import { takeLatest, put, call, select } from "redux-saga/effects";
import { message } from "antd";

import { getBuildings } from "../../api";
import { LOAD_BUILDINGS, LOAD_BUILDINGS_SUCCESS } from "../reducers/buildings";
import { LOAD_SERVICES_FAILED } from "../reducers/services";
import { isEmpty } from "ramda";
import { selectBuildings } from "../selectors/buildings";


export default function* main() {
    yield takeLatest(LOAD_BUILDINGS, fetchBuilding);
}

function* fetchBuilding() {
    try {
        const state = yield select();
        if (!isEmpty(selectBuildings(state))) {
            yield  yield put({ type: LOAD_BUILDINGS_SUCCESS, payload: selectBuildings(state) })
        }
        const list = yield call(getBuildings);

        yield put({ type: LOAD_BUILDINGS_SUCCESS, payload: list })
    } catch (error) {
        message.error('Ошибка загрузки зданий.');
        yield put({ type: LOAD_SERVICES_FAILED, payload: error })
    }
}
