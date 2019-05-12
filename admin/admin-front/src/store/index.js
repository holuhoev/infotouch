import { applyMiddleware, combineReducers, compose, createStore } from 'redux';
import createSagaMiddleware from 'redux-saga'

import sagas from './sagas'
import map from './reducers/map'
import createdPoints from "./reducers/createdPoints";


const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const sagaMiddleware   = createSagaMiddleware();

const rootReducer = combineReducers({
    map,
    createdPoints
});


const store = createStore(
    rootReducer,
    composeEnhancers(applyMiddleware(sagaMiddleware))
);

sagaMiddleware.run(sagas);

export default store;