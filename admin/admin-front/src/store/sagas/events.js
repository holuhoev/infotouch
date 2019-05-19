import { call, put, select, takeLatest } from "redux-saga/effects";


import { message } from "antd";

import {
    DELETE_EVENT,
    DELETE_EVENT_FAILED,
    DELETE_EVENT_SUCCESS,
    LOAD_EVENTS,
    LOAD_EVENTS_FAILED,
    LOAD_EVENTS_SUCCESS,
    SAVE_NEW_EVENT_SUCCESS,
    SAVE_EVENT,
    SAVE_EVENT_FAILED,
    SAVE_EVENT_SUCCESS,
} from "../reducers/events";
import { createEvent, deleteEventById, getEvents, putEvent } from "../../api";

import { selectDeviceId } from "../selectors/map";


export default function* main() {
    yield takeLatest(LOAD_EVENTS, fetchEvents);
    yield takeLatest(SAVE_EVENT, saveEvent);
    yield takeLatest(DELETE_EVENT, deleteEvent);
}

function* saveEvent() {
    const state      = yield select();
    const eventToSave = state.events.editable;

    try {
        if (eventToSave.isNew) {
            eventToSave[ 'deviceId' ] = selectDeviceId(state);
        }

        const savedEvent = yield call((eventToSave.isNew ? createEvent : putEvent), eventToSave, eventToSave.id,);

        message.success(`Мероприятие с id=${ savedEvent.id } сохранено`);
        yield put({
            type:    (eventToSave.isNew ? SAVE_NEW_EVENT_SUCCESS : SAVE_EVENT_SUCCESS),
            payload: savedEvent
        })
    } catch (error) {
        message.error(`Ошибка сохранения мероприятия`);
        yield put({ type: SAVE_EVENT_FAILED, payload: error })
    }
}

function* fetchEvents(action) {
    try {
        const state      = yield select();
        const deviceId = action.payload || selectDeviceId(state) || null;
        const events      = yield call(getEvents, deviceId);

        yield put({ type: LOAD_EVENTS_SUCCESS, payload: events })
    } catch (error) {
        message.error('Ошибка загрузки списка мероприятий');
        yield put({ type: LOAD_EVENTS_FAILED, payload: error })
    }
}


function* deleteEvent(action) {
    const id = action.payload;
    try {
        yield call(deleteEventById, id);

        yield put({ type: DELETE_EVENT_SUCCESS, payload: id });
        message.info(`Мероприятие с id =${ id } удалено`);
    } catch (error) {
        message.error(`Ошибка удаления мероприятия с id =${ id }`);
        yield put({ type: DELETE_EVENT_FAILED, payload: error })
    }
}

