import { fork } from 'redux-saga/effects'

import map from './map'

export default function* main() {
    yield fork(map);
};