import { takeLatest, put, call, select } from "redux-saga/effects";
import { message } from 'antd';

import {
    LOAD,
    LOAD_SUCCESS,
    LOAD_FAILED,
    SAVE_CREATED_POINTS,
    SAVE_CREATED_POINTS_FAILED,
    SAVE_CREATED_POINTS_SUCCESS,
    SAVE_CREATED_EDGES_SUCCESS,
    SAVE_CREATED_EDGES_FAILED,
    SAVE_CREATED_EDGES,
    DELETE_POINT, DELETE_POINT_SUCCESS, DELETE_POINT_FAILED
} from "../reducers/map";
import {
    createNewEdges,
    createNewPoints,
    deletePointById,
    getBuildingMap
} from "../../api";
import {
    selectBuildingId,
    selectElementIdByCoordinates,
    selectSchemeCreatedPoints,
    selectSchemeEdgesForSave
} from "../selectors/map";
import { filter } from "ramda";


export default function* main() {
    yield takeLatest(LOAD, fetchBuildingMap);
    yield takeLatest(SAVE_CREATED_POINTS, saveCreatedPoints);
    yield takeLatest(SAVE_CREATED_EDGES, saveCreatedEdges);
    yield takeLatest(DELETE_POINT, deletePoint)
}

const pointHasSchemeElement = point => !!point.schemeElementId;

function* fetchBuildingMap(action) {
    try {
        const state      = yield select();
        const buildingId = action.payload || selectBuildingId(state);

        if (!buildingId) {
            yield put({ type: LOAD_FAILED, payload: "Выберите здание." })
            return;
        }

        const buildingMap = yield call(getBuildingMap, buildingId);

        yield put({ type: LOAD_SUCCESS, payload: buildingMap })
    } catch (error) {
        message.error('Ошибка загрузки карты');
        yield put({ type: LOAD_FAILED, payload: error })
    }
}

function* saveCreatedPoints() {
    try {
        const state  = yield select();
        const points = selectSchemeCreatedPoints(state);

        const data = {
            points:           filter(pointHasSchemeElement, points.map(point => ({
                ...point,
                schemeElementId: selectElementIdByCoordinates(state, [ point.x, point.y ])
            })))
        };

        const saved = yield call(createNewPoints, data);

        message.success('Сохранено');
        yield put({ type: SAVE_CREATED_POINTS_SUCCESS, payload: saved })
    } catch (error) {
        message.error('Ошибка сохранения');
        yield put({ type: SAVE_CREATED_POINTS_FAILED, payload: error })
    }
}

function* saveCreatedEdges() {
    try {
        const state = yield select();
        const data  = {
            edges: selectSchemeEdgesForSave(state)
        };

        const saved = yield call(createNewEdges, data);

        message.success('Сохранено');
        yield put({ type: SAVE_CREATED_EDGES_SUCCESS, payload: saved })
    } catch (error) {
        message.error('Ошибка сохранения');
        yield put({ type: SAVE_CREATED_EDGES_FAILED, payload: error })
    }
}

function* deletePoint(action) {
    try{
        const id = action.payload;

        yield call(deletePointById, id);

        message.info("Точка удалена");
        yield put({type: DELETE_POINT_SUCCESS, payload: id});
    } catch (error) {
        message.error('Ошибка удаления');
        yield put({type: DELETE_POINT_FAILED, payload: error})
    }
}