import { fork } from 'redux-saga/effects'

import map from './map'
import device from "./device";

export default function* main() {
    yield fork(map);
    yield fork(device);
};