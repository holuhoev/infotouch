import React from 'react';
import 'antd/dist/antd.css';
import { Link, Redirect, Route, Switch } from "react-router-dom";
import { Layout, Menu, Breadcrumb } from 'antd';

import './App.scss';
import { ROUTE } from "../../utils/routes";
import MapPage from "../pages/map/MapPage";
import ServicePage from "../pages/service/ServicePage";
import DevicePage from "../pages/device/DevicePage";

const { Header, Content, Footer } = Layout;


function App() {

    return (
        <div className="root-container">
            <Layout className="layout">
                <Header style={ {
                    backgroundColor: '#fff'
                } }>
                    <div className="logo"/>
                    <Menu
                        theme="light"
                        mode="horizontal"
                        defaultSelectedKeys={ [ '1' ] }
                        style={ { lineHeight: '64px' } }
                    >
                        <Menu.Item key="1"><Link to={ ROUTE.MAP }>Карта</Link></Menu.Item>
                        <Menu.Item key="2"><Link to={ ROUTE.DEVICE }>Устройства</Link></Menu.Item>
                        <Menu.Item key="3"><Link to={ ROUTE.SERVICE }>Услуги</Link></Menu.Item>
                    </Menu>
                </Header>
                <Content style={ { padding: '0 50px' } }>
                    <Breadcrumb style={ { margin: '16px 0' } }>
                        <Breadcrumb.Item></Breadcrumb.Item>
                    </Breadcrumb>
                    <div style={ { background: '#fff', padding: 24, minHeight: 280, height: '100%' } }>
                        <Switch>
                            <Route exact path={ ROUTE.CORE } render={ () => <Redirect to={ ROUTE.MAP }/> }/>
                            <Route path={ ROUTE.MAP } component={ MapPage }/>
                            <Route path={ ROUTE.SERVICE } component={ ServicePage }/>
                            <Route path={ ROUTE.DEVICE } component={ DevicePage }/>

                        </Switch>
                    </div>
                </Content>
                <Footer style={ { textAlign: 'center' } }>НИУ ВШЭ ©2019</Footer>
            </Layout>

        </div>
    );
}

export default App;
