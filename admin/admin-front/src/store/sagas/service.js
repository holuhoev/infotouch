import { call, put, select, takeLatest } from "redux-saga/effects";


import { message } from "antd";

import {
    LOAD_SERVICES,
    LOAD_SERVICES_FAILED,
    LOAD_SERVICES_SUCCESS,
    SAVE_NEW_SERVICE_SUCCESS,
    SAVE_SERVICE,
    SAVE_SERVICE_FAILED,
    SAVE_SERVICE_SUCCESS
} from "../reducers/services";
import { createService, getServices, putService } from "../../api";

import { selectBuildingId } from "../selectors/map";

export default function* main() {
    yield takeLatest(LOAD_SERVICES, fetchServices);
    yield takeLatest(SAVE_SERVICE, saveService);
}

function* saveService() {
    const state         = yield select();
    const serviceToSave = state.services.editable;

    try {
        if (serviceToSave.isNew) {
            serviceToSave[ 'buildingId' ] = selectBuildingId(state);
        }

        const savedService = yield call((serviceToSave.isNew ? createService : putService), serviceToSave, serviceToSave.id,);

        message.success(`Услуга с id=${ savedService.id } сохранена`);
        yield put({
            type:    (serviceToSave.isNew ? SAVE_NEW_SERVICE_SUCCESS : SAVE_SERVICE_SUCCESS),
            payload: savedService
        })
    } catch (error) {
        message.error(`Ошибка сохранения услуги`);
        yield put({ type: SAVE_SERVICE_FAILED, payload: error })
    }
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

