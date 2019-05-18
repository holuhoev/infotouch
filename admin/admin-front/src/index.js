import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import { Provider } from "react-redux";
import moment from 'moment';
import 'moment/locale/ru';
import { ConfigProvider } from "antd";

import { context } from './utils/url';
import App from './components/app/App';
import store from "./store";
import './utils/axios';
import './index.scss';
import Empty from "./components/common/empty/Empty";

moment.locale('ru');

ReactDOM.render(
    <Provider store={ store }>
        <BrowserRouter basename={ context }>
            <ConfigProvider
                renderEmpty={ () => (<Empty/>) }
            >
                <App/>
            </ConfigProvider>
        </BrowserRouter>
    </Provider>,
    document.getElementById('root')
);
