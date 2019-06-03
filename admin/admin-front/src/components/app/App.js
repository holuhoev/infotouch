import React, { Component } from 'react';
import 'antd/dist/antd.css';
import { Link, Redirect, Route, Switch, withRouter } from "react-router-dom";
import { Layout, Menu, Breadcrumb, Button } from 'antd';

import './App.scss';
import { ROUTE } from "../../utils/routes";
import MapPage from "../pages/map/MapPage";
import ServicePage from "../pages/service/ServicePage";
import DevicePage from "../pages/device/DevicePage";
import UnitPage from "../pages/unit/UnitPage";
import EventPage from "../pages/event/EventPage";
import AnnouncementPage from "../pages/announcement/AnnouncementPage";
import { isLoggedIn } from "../../utils/axios";
import LoginPage from "../pages/login/LoginPage";
import { logout } from "../../utils/auth";
import * as PropTypes from "prop-types";

const { Header, Content, Footer } = Layout;

const withRedirect = (Component) => (props) => {
    if (isLoggedIn()) {

        return <Component { ...props } />;
    }
    return (
        <Redirect to={ { pathname: ROUTE.LOGIN, state: { from: props.location } } }/>
    );
};


class App extends Component {


    quit = () => logout().then(() => {
        this.props.history.push(ROUTE.LOGIN)
    });

    render() {
        let { location } = this.props;

        return (
            <div className="root-container">
                <Layout className="layout">
                    <Header style={ {
                        backgroundColor: '#fff',
                        display:         'flex',
                        justifyContent:  'space-between',
                        alignItems:      'center'
                    } }>
                        { (isLoggedIn()) && [ (
                            <Menu
                                theme="light"
                                mode="horizontal"
                                selectedKeys={ [ location.pathname ] }
                                style={ { lineHeight: '64px' } }
                            >
                                <Menu.Item key={ ROUTE.MAP }>
                                    <Link to={ ROUTE.MAP }>Карта</Link>
                                </Menu.Item>
                                <Menu.Item key={ ROUTE.DEVICE }>
                                    <Link to={ ROUTE.DEVICE }>Устройства</Link>
                                </Menu.Item>
                                <Menu.Item key={ ROUTE.UNIT }>
                                    <Link to={ ROUTE.UNIT }>Подразделения</Link>
                                </Menu.Item>
                                <Menu.Item key={ ROUTE.SERVICE }>
                                    <Link to={ ROUTE.SERVICE }>Услуги</Link>
                                </Menu.Item>
                                <Menu.Item key={ ROUTE.EVENT }>
                                    <Link to={ ROUTE.EVENT }>Мероприятия</Link>
                                </Menu.Item>
                                <Menu.Item key={ ROUTE.ANNOUNCEMENT }>
                                    <Link to={ ROUTE.ANNOUNCEMENT }>Объявления</Link>
                                </Menu.Item>
                            </Menu>
                        ), (
                            <Button
                                onClick={ this.quit }
                                type={ "link" }
                            >
                                Выйти
                            </Button>
                        ) ] }


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
                                <Route path={ ROUTE.LOGIN }
                                       render={
                                           (props) => isLoggedIn()
                                               ? <Redirect to={ ROUTE.MAP }/>
                                               : <LoginPage { ...props } />
                                       }
                                />
                                <Route exact
                                       path={ ROUTE.CORE }
                                       render={ () => <Redirect to={ ROUTE.MAP }/> }
                                />
                                <Route path={ ROUTE.MAP } component={ withRedirect(MapPage) }/>
                                <Route path={ ROUTE.SERVICE } component={ withRedirect(ServicePage) }/>
                                <Route path={ ROUTE.DEVICE } component={ withRedirect(DevicePage) }/>
                                <Route path={ ROUTE.UNIT } component={ withRedirect(UnitPage) }/>
                                <Route path={ ROUTE.EVENT } component={ withRedirect(EventPage) }/>
                                <Route path={ ROUTE.ANNOUNCEMENT } component={ withRedirect(AnnouncementPage) }/>

                            </Switch>
                        </div>
                    </Content>
                    <Footer style={ { textAlign: 'center' } }>НИУ ВШЭ ©2019</Footer>
                </Layout>

            </div>
        );
    }
}

App.propTypes = { location: PropTypes.any }

export default withRouter(App);
