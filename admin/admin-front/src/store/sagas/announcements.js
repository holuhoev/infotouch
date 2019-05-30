import { call, put, select, takeLatest } from "redux-saga/effects";


import { message } from "antd";

import {
  DELETE_ANNOUNCEMENT,
  DELETE_ANNOUNCEMENT_FAILED,
  DELETE_ANNOUNCEMENT_SUCCESS,
  LOAD_ANNOUNCEMENTS,
  LOAD_ANNOUNCEMENTS_FAILED,
  LOAD_ANNOUNCEMENTS_SUCCESS,
  SAVE_NEW_ANNOUNCEMENT_SUCCESS,
  SAVE_ANNOUNCEMENT,
  SAVE_ANNOUNCEMENT_FAILED,
  SAVE_ANNOUNCEMENT_SUCCESS,
} from "../reducers/announcements";
import { createAnnouncement, deleteAnnouncementById, getAnnouncements, putAnnouncement } from "../../api";

import { selectDeviceId } from "../selectors/map";


export default function* main() {
  yield takeLatest(LOAD_ANNOUNCEMENTS, fetchAnnouncements);
  yield takeLatest(SAVE_ANNOUNCEMENT, saveAnnouncement);
  yield takeLatest(DELETE_ANNOUNCEMENT, deleteAnnouncement);
}

function* saveAnnouncement() {
  const state      = yield select();
  const announcementToSave = state.announcements.editable;

  try {
    if (announcementToSave.isNew) {
      announcementToSave[ 'deviceId' ] = selectDeviceId(state);
    }

    const savedAnnouncement = yield call((announcementToSave.isNew ? createAnnouncement : putAnnouncement), announcementToSave, announcementToSave.id,);

    message.success(`Объявление сохранено`);
    yield put({
      type:    (announcementToSave.isNew ? SAVE_NEW_ANNOUNCEMENT_SUCCESS : SAVE_ANNOUNCEMENT_SUCCESS),
      payload: savedAnnouncement
    })
  } catch (error) {
    message.error(`Ошибка сохранения объявления`);
    yield put({ type: SAVE_ANNOUNCEMENT_FAILED, payload: error })
  }
}

function* fetchAnnouncements(action) {
  try {
    const state      = yield select();
    const deviceId = action.payload || selectDeviceId(state) || null;
    const announcements      = yield call(getAnnouncements, deviceId);

    yield put({ type: LOAD_ANNOUNCEMENTS_SUCCESS, payload: announcements })
  } catch (error) {
    message.error('Ошибка загрузки списка объявлений');
    yield put({ type: LOAD_ANNOUNCEMENTS_FAILED, payload: error })
  }
}


function* deleteAnnouncement(action) {
  const id = action.payload;
  try {
    yield call(deleteAnnouncementById, id);

    yield put({ type: DELETE_ANNOUNCEMENT_SUCCESS, payload: id });
    message.info(`Мероприятие с id =${ id } удалено`);
  } catch (error) {
    message.error(`Ошибка удаления объявления с id =${ id }`);
    yield put({ type: DELETE_ANNOUNCEMENT_FAILED, payload: error })
  }
}

