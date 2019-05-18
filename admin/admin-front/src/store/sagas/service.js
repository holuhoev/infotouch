import { takeLatest, put, call, delay, select } from "redux-saga/effects";


import { message } from "antd";

import { LOAD_SERVICES, LOAD_SERVICES_FAILED, LOAD_SERVICES_SUCCESS } from "../reducers/services";
import { getServices } from "../../api";

import { selectBuildingId } from "../selectors/map";

export default function* main() {
    yield takeLatest(LOAD_SERVICES, fetchServices);
}


function* fetchServices(action) {
    try {
        const state      = yield select();
        const buildingId = action.payload || selectBuildingId(state) || null;
        const services   = yield call(getServices, buildingId);

        yield put({ type: LOAD_SERVICES_SUCCESS, payload: services })
    } catch (error) {
        message.error('Ошибка загрузки устройств');
        yield put({ type: LOAD_SERVICES_FAILED, payload: error })
    }
}
