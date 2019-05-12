import { applyMiddleware, combineReducers, compose, createStore } from 'redux';
import createSagaMiddleware from 'redux-saga'
import undoable, { includeAction } from 'redux-undo'

import map, { CREATE_POINT, REDO_CREATE_POINT, UNDO_CREATE_POINT } from './reducers/map'
import sagas from './sagas'

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const sagaMiddleware   = createSagaMiddleware();

const rootReducer = combineReducers({
    map: map
    //     undoable(map, {
    //     filter: includeAction([CREATE_POINT]),
    //     limit: 100,
    //     debug: true,
    //     undoType: UNDO_CREATE_POINT,
    //     redoType: REDO_CREATE_POINT
    // })
});


const store = createStore(
    rootReducer,
    composeEnhancers(applyMiddleware(sagaMiddleware))
);

sagaMiddleware.run(sagas);

export default store;