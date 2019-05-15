import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import { Provider } from "react-redux";
import { context } from './utils/url';
import moment from 'moment';
import 'moment/locale/ru';

import './utils/axios';
import './index.scss';
import App from './components/app/App';
import store from "./store";

moment.locale('ru');

ReactDOM.render(
    <Provider store={ store }>
        <BrowserRouter basename={ context }>
            <App/>
        </BrowserRouter>
    </Provider>,
    document.getElementById('root')
);