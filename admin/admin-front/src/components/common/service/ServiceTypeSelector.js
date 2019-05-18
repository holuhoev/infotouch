import React from 'react';
import { bindActionCreators } from "redux";
import { connect } from "react-redux";

import { loadServices } from "../../../store/reducers/services";
import { loadBuildings } from "../../../store/reducers/buildings";


function ServiceTypeSelector({}) {

}


const mapStateToProps = (state) => {

    return {
        servicesLoading: state.application.servicesLoading,
        services:        state.services.list
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    loadServices,
    loadBuildings
}, dispatch);


export default connect(mapStateToProps, mapDispatchToProps)(ServiceTypeSelector)