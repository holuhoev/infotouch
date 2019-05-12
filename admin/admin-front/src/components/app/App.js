import React from 'react';
import './App.scss';
import { Redirect, Route, Switch } from "react-router-dom";
import { ROUTE } from "../../utils/routes";
import MapPage from "../pages/map/MapPage";

function App() {

    return (
        <div className="root-container">
            <Switch>
                <Route exact path={ ROUTE.CORE } render={ () => <Redirect to={ ROUTE.MAP }/> }/>
                <Route path={ ROUTE.MAP } component={ MapPage }/>
            </Switch>
        </div>
    );
}

export default App;
