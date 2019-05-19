import { takeLatest, put, call, select } from "redux-saga/effects";


import { message } from "antd";
import {
    DELETE_DEVICE, DELETE_DEVICE_FAILED,
    DELETE_DEVICE_SUCCESS,
    LOAD_DEVICES,
    LOAD_DEVICES_FAILED,
    LOAD_DEVICES_SUCCESS,
    LOAD_ONE_DEVICE,
    LOAD_ONE_DEVICE_FAILED,
    LOAD_ONE_DEVICE_SUCCESS,
    SAVE_DEVICE,
    SAVE_DEVICE_FAILED,
    SAVE_DEVICE_SUCCESS,
    SAVE_NEW_DEVICE_SUCCESS
} from "../reducers/devices";
import { createDevice, deleteDeviceById, getDeviceById, getDevices, putDevice } from "../../api";

export default function* main() {
    yield takeLatest(LOAD_DEVICES, fetchDeviceList);
    yield takeLatest(LOAD_ONE_DEVICE, fetchOneDeviceById);
    yield takeLatest(SAVE_DEVICE, saveDevice);
    yield takeLatest(DELETE_DEVICE, deleteDevice);
}

function* fetchDeviceList(action) {
    try {
        const searchString = action.payload;

        const deviceList = yield call(getDevices, searchString);

        yield put({ type: LOAD_DEVICES_SUCCESS, payload: deviceList })
    } catch (error) {
        message.error('Ошибка загрузки устройств');
        yield put({ type: LOAD_DEVICES_FAILED, payload: error })
    }
}

function* fetchOneDeviceById(action) {
    try {

        const { id }     = action.payload;
        const deviceList = yield call(getDeviceById, id);

        yield put({ type: LOAD_ONE_DEVICE_SUCCESS, payload: deviceList })
    } catch (error) {
        message.error(`Ошибка загрузки устройства с id=${ action.payload.id }`);
        yield put({ type: LOAD_ONE_DEVICE_FAILED, payload: error })
    }
}

function* saveDevice() {
    const state        = yield select();
    const deviceToSave = state.devices.editable;

    try {
        const savedDevice = yield call((deviceToSave.isNew ? createDevice : putDevice), deviceToSave, deviceToSave.id,);

        message.success(`Устройство с id=${ savedDevice.id } сохранено`);
        yield put({ type: (deviceToSave.isNew ? SAVE_NEW_DEVICE_SUCCESS : SAVE_DEVICE_SUCCESS), payload: savedDevice })
    } catch (error) {
        message.error(`Ошибка сохранения устройства`);
        yield put({ type: SAVE_DEVICE_FAILED, payload: error })
    }
}

function* deleteDevice(action) {
    try {
        const id = action.payload;
        yield call(deleteDeviceById, id);

        message.success(`Устройство с id=${ id } удалено`);
        yield put({ type: DELETE_DEVICE_SUCCESS, payload: id })
    } catch (error) {
        message.error(`Ошибка удаления устройства с id=${ action.payload }`);
        yield put({ type: DELETE_DEVICE_FAILED, payload: error })
    }
}