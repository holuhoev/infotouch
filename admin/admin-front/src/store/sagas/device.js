import { takeLatest, put, call, delay, select } from "redux-saga/effects";


import { message } from "antd";
import {
    LOAD_DEVICES,
    LOAD_DEVICES_FAILED,
    LOAD_DEVICES_SUCCESS,
    LOAD_ONE_DEVICE,
    LOAD_ONE_DEVICE_FAILED,
    LOAD_ONE_DEVICE_SUCCESS,
    SAVE_DEVICE, SAVE_DEVICE_FAILED, SAVE_DEVICE_SUCCESS, SAVE_NEW_DEVICE_SUCCESS
} from "../reducers/devices";
import { createDevice, getDeviceById, getDevices, putDevice } from "../../api";

export default function* main() {
    yield takeLatest(LOAD_DEVICES, fetchDeviceList);
    yield takeLatest(LOAD_ONE_DEVICE, fetchOneDeviceById);
    yield takeLatest(SAVE_DEVICE, saveDevice);
}

function* fetchDeviceList() {
    try {
        yield delay(1000);
        const deviceList = yield call(getDevices);

        yield put({ type: LOAD_DEVICES_SUCCESS, payload: deviceList })
    } catch (error) {
        message.error('Ошибка загрузки устройств');
        yield put({ type: LOAD_DEVICES_FAILED, payload: error })
    }
}

function* fetchOneDeviceById(action) {
    try {
        yield delay(1000);

        const { id }     = action.payload;
        const deviceList = yield call(getDeviceById, id);

        yield put({ type: LOAD_ONE_DEVICE_SUCCESS, payload: deviceList })
    } catch (error) {
        message.error(`Ошибка загрузки устройства с id=${ action.payload.id }`);
        yield put({ type: LOAD_ONE_DEVICE_FAILED, payload: error })
    }
}

function* saveDevice() {
    yield delay(1000);

    const state        = yield select();
    const deviceToSave = state.devices.editable;

    console.log(deviceToSave);
    try {
        const savedDevice = yield call((deviceToSave.isNew ? createDevice : putDevice), deviceToSave, deviceToSave.id,);

        message.success(`Устройство с id=${ savedDevice.id } сохранено`);
        yield put({ type: (deviceToSave.isNew? SAVE_NEW_DEVICE_SUCCESS: SAVE_DEVICE_SUCCESS), payload: savedDevice })
    } catch (error) {
        message.error(`Ошибка сохранения устройства`);
        yield put({ type: SAVE_DEVICE_FAILED, payload: error })
    }
}