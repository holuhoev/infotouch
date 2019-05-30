import { fork } from 'redux-saga/effects'

import map from './map'
import device from "./device";
import service from "./service";
import building from "./building";
import units from "./units";
import events from "./events";
import announcements from "./announcements";

export default function* main() {
    yield fork(map);
    yield fork(device);
    yield fork(service);
    yield fork(building);
    yield fork(units);
    yield fork(events);
    yield fork(announcements);
};