import { takeLatest, put, call ,delay} from "redux-saga/effects";


import { message } from "antd";
import {
    LOAD_DEVICES,
    LOAD_DEVICES_FAILED,
    LOAD_DEVICES_SUCCESS
} from "../reducers/devices";
import { getDevices } from "../../api";

export default function* main() {
    yield takeLatest(LOAD_DEVICES, fetchBuildingMap);
}

function* fetchBuildingMap(action) {
    try {
        yield delay(1000);
        const deviceList = yield call(getDevices);

        yield put({ type: LOAD_DEVICES_SUCCESS, payload: deviceList })
    } catch (error) {
        message.error('Ошибка загрузки устройств');
        yield put({ type: LOAD_DEVICES_FAILED, payload: error })
    }
}