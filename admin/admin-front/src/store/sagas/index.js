import { fork } from 'redux-saga/effects'

import map from './map'
import device from "./device";
import service from "./service";

export default function* main() {
    yield fork(map);
    yield fork(device);
    yield fork(service);
};