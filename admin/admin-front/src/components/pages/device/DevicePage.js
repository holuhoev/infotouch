import React, { Component } from "react";

import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import { loadDevices } from "../../../store/reducers/devices";

class DevicePage extends Component {

    componentDidMount() {
        this.props.loadDevices();
    }

    render() {
        const { devices } = this.props;

        return (
            <div>
                Device Page
            </div>
        )
    }
}


const mapStateToProps = (state) => {

    return {
        devices: state.devices
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    loadDevices
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(DevicePage);