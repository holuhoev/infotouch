import { call, put, select, takeLatest } from "redux-saga/effects";


import { message } from "antd";

import {
    DELETE_SERVICE,
    DELETE_SERVICE_FAILED,
    DELETE_SERVICE_SUCCESS,
    LOAD_SERVICES,
    LOAD_SERVICES_FAILED,
    LOAD_SERVICES_SUCCESS,
    SAVE_NEW_SERVICE_SUCCESS,
    SAVE_SERVICE,
    SAVE_SERVICE_FAILED,
    SAVE_SERVICE_SUCCESS,
    SAVE_SERVICES_POINTS, SAVE_SERVICES_POINTS_FAILED, SAVE_SERVICES_POINTS_SUCCESS
} from "../reducers/services";
import { createService, deleteServiceById, getServices, putService, putServicesPoints } from "../../api";

import { selectBuildingId } from "../selectors/map";
import { selectForSavePoints } from "../selectors/services";

export default function* main() {
    yield takeLatest(LOAD_SERVICES, fetchServices);
    yield takeLatest(SAVE_SERVICE, saveService);
    yield takeLatest(DELETE_SERVICE, deleteService);
    yield takeLatest(SAVE_SERVICES_POINTS, saveServicesPoints);
}

function* saveServicesPoints() {
    const state  = yield select();
    const toSave = selectForSavePoints(state);

    try {
        yield call(putServicesPoints, toSave);

        yield put({
            type:    SAVE_SERVICES_POINTS_SUCCESS,
            payload: []
        })
    } catch (error) {
        message.error(`Ошибка сохранения услуги`);
        yield put({ type: SAVE_SERVICES_POINTS_FAILED, payload: error })
    }
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
        message.error('Ошибка загрузки списка услуг');
        yield put({ type: LOAD_SERVICES_FAILED, payload: error })
    }
}


function* deleteService(action) {
    const id = action.payload;
    try {
        yield call(deleteServiceById, id);

        yield put({ type: DELETE_SERVICE_SUCCESS, payload: id });
        message.info(`Услуга с id =${ id } удалена`);
    } catch (error) {
        message.error(`Ошибка удаления услуги с id =${ id }`);
        yield put({ type: DELETE_SERVICE_FAILED, payload: error })
    }
}

