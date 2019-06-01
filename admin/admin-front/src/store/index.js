import { applyMiddleware, combineReducers, compose, createStore } from 'redux';
import createSagaMiddleware from 'redux-saga'

import sagas from './sagas'
import map from './reducers/map'
import createdPoints from "./reducers/createdPoints";
import createdEdges from "./reducers/createdEdges";
import { application } from "./reducers/application";
import { devices } from "./reducers/devices";
import { services } from "./reducers/services";
import buildings from "./reducers/buildings";
import { units } from "./reducers/units";
import { events } from "./reducers/events";
import { announcements } from "./reducers/announcements";


const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const sagaMiddleware   = createSagaMiddleware();

const rootReducer = combineReducers({
    map,
    createdPoints,
    createdEdges,
    application,
    devices,
    services,
    buildings,
    units,
    events,
    announcements,
});


const store = createStore(
    rootReducer,
    composeEnhancers(applyMiddleware(sagaMiddleware))
);

sagaMiddleware.run(sagas);

export default store;