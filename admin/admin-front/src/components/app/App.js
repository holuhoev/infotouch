import React from 'react';
import 'antd/dist/antd.css';
import { Link, Redirect, Route, Switch, withRouter } from "react-router-dom";
import { Layout, Menu, Breadcrumb } from 'antd';

import './App.scss';
import { ROUTE } from "../../utils/routes";
import MapPage from "../pages/map/MapPage";
import ServicePage from "../pages/service/ServicePage";
import DevicePage from "../pages/device/DevicePage";
import UnitPage from "../pages/unit/UnitPage";
import EventPage from "../pages/event/EventPage";
import AnnouncementPage from "../pages/announcement/AnnouncementPage";

const { Header, Content, Footer } = Layout;

function App({ location }) {

    return (
        <div className="root-container">
            <Layout className="layout">
                <Header style={ {
                    backgroundColor: '#fff'
                } }>
                    {/*<div className="logo">*/ }
                    {/*<img style={{height: 40, width: 50}}*/ }
                    {/*src={"https://www.hse.ru/data/2014/06/24/1310196971/logo_%D1%81_hse_Pantone286.jpg"}*/ }
                    {/*/>*/ }
                    {/*</div>*/ }
                    <Menu
                        theme="light"
                        mode="horizontal"
                        selectedKeys={ [ location.pathname ] }
                        style={ { lineHeight: '64px' } }
                    >
                        <Menu.Item key={ ROUTE.MAP }><Link
                            to={ ROUTE.MAP }>Карта</Link></Menu.Item>
                        <Menu.Item key={ ROUTE.DEVICE }><Link
                            to={ ROUTE.DEVICE }>Устройства</Link></Menu.Item>
                        <Menu.Item key={ ROUTE.UNIT }><Link
                            to={ ROUTE.UNIT }>Подразделения</Link></Menu.Item>
                        <Menu.Item key={ ROUTE.SERVICE }><Link
                            to={ ROUTE.SERVICE }>Услуги</Link></Menu.Item>
                        <Menu.Item key={ ROUTE.EVENT }><Link
                            to={ ROUTE.EVENT }>Мероприятия</Link></Menu.Item><
                        Menu.Item key={ ROUTE.ANNOUNCEMENT }><Link
                        to={ ROUTE.ANNOUNCEMENT }>Объявления</Link></Menu.Item>

                    </Menu>
                </Header>
                <Content style={ { padding: '0 50px', minHeight: 600 } }>
                    <Breadcrumb style={ { margin: '16px 0' } }>
                        <Breadcrumb.Item></Breadcrumb.Item>
                    </Breadcrumb>
                    <div style={ {
                        background: '#fff',
                        padding:    24,
                        minHeight:  280,
                        height:     '100%'
                    } }>
                        <Switch>
                            <Route exact path={ ROUTE.CORE }
                                   render={ () => <Redirect to={ ROUTE.MAP }/> }/>
                            <Route path={ ROUTE.MAP } component={ MapPage }/>
                            <Route path={ ROUTE.SERVICE } component={ ServicePage }/>
                            <Route path={ ROUTE.DEVICE } component={ DevicePage }/>
                            <Route path={ ROUTE.UNIT } component={ UnitPage }/>
                            <Route path={ ROUTE.EVENT } component={ EventPage }/>
                            <Route path={ ROUTE.ANNOUNCEMENT } component={ AnnouncementPage }/>

                        </Switch>
                    </div>
                </Content>
                <Footer style={ { textAlign: 'center' } }>НИУ ВШЭ ©2019</Footer>
            </Layout>

        </div>
    );
}

export default withRouter(App);
