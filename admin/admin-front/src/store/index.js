import { applyMiddleware, combineReducers, compose, createStore } from 'redux';
import createSagaMiddleware from 'redux-saga'

import sagas from './sagas'
import map from './reducers/map'
import createdPoints from "./reducers/createdPoints";
import createdEdges from "./reducers/createdEdges";
import { application } from "./reducers/application";
import { devices } from "./reducers/devices";
import { services } from "./reducers/services";


const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const sagaMiddleware   = createSagaMiddleware();

const rootReducer = combineReducers({
    map,
    createdPoints,
    createdEdges,
    application,
    devices,
    services
});


const store = createStore(
    rootReducer,
    composeEnhancers(applyMiddleware(sagaMiddleware))
);

sagaMiddleware.run(sagas);

export default store;