import { call, put, select, takeLatest } from "redux-saga/effects";


import { message } from "antd";

import {
    DELETE_UNIT,
    DELETE_UNIT_FAILED,
    DELETE_UNIT_SUCCESS,
    LOAD_UNITS,
    LOAD_UNITS_FAILED,
    LOAD_UNITS_SUCCESS,
    SAVE_NEW_UNIT_SUCCESS,
    SAVE_UNIT,
    SAVE_UNIT_FAILED,
    SAVE_UNIT_SUCCESS,
} from "../reducers/units";
import { createUnit, deleteUnitById, getUnits, putUnit } from "../../api";

import { selectBuildingId } from "../selectors/map";


export default function* main() {
    yield takeLatest(LOAD_UNITS, fetchUnits);
    yield takeLatest(SAVE_UNIT, saveUnit);
    yield takeLatest(DELETE_UNIT, deleteUnit);
}

function* saveUnit() {
    const state      = yield select();
    const unitToSave = state.units.editable;

    try {
        if (unitToSave.isNew) {
            unitToSave[ 'buildingId' ] = selectBuildingId(state);
        }

        const savedUnit = yield call((unitToSave.isNew ? createUnit : putUnit), unitToSave, unitToSave.id,);

        message.success(`Подразделение с id=${ savedUnit.id } сохранена`);
        yield put({
            type:    (unitToSave.isNew ? SAVE_NEW_UNIT_SUCCESS : SAVE_UNIT_SUCCESS),
            payload: savedUnit
        })
    } catch (error) {
        message.error(`Ошибка сохранения подразделений`);
        yield put({ type: SAVE_UNIT_FAILED, payload: error })
    }
}

function* fetchUnits(action) {
    try {
        const state      = yield select();
        const buildingId = action.payload || selectBuildingId(state) || null;
        const units      = yield call(getUnits, buildingId);

        yield put({ type: LOAD_UNITS_SUCCESS, payload: units })
    } catch (error) {
        message.error('Ошибка загрузки списка подразделений');
        yield put({ type: LOAD_UNITS_FAILED, payload: error })
    }
}


function* deleteUnit(action) {
    const id = action.payload;
    try {
        yield call(deleteUnitById, id);

        yield put({ type: DELETE_UNIT_SUCCESS, payload: id });
        message.info(`Подразделение с id =${ id } удалена`);
    } catch (error) {
        message.error(`Ошибка удаления подразделения с id =${ id }`);
        yield put({ type: DELETE_UNIT_FAILED, payload: error })
    }
}

