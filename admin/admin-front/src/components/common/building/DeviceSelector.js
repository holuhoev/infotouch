import { Select } from "antd";
import React from "react";
import { bindActionCreators } from "redux";
import { connect } from "react-redux";

import { searchDevice, selectDevice } from "../../../store/reducers/devices";


const Option = Select.Option;

function DeviceSelector({ devices, selected, selectDevice, searchDevice, style }) {

    return (
        <Select
            labelInValue
            placeholder="Введите устройство"
            value={ selected }
            onSearch={ searchDevice }
            onChange={ selectDevice }
            style={ style }
        >
            { devices.map(d => (
                <Option key={ d.id }>{ d.title }</Option>
            )) }
        </Select>
    )
}


const mapStateToProps = (state) => {

    return {
        devices:  state.devices.list,
        selected: state.application.selectedDevice,
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    searchDevice,
    selectDevice
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(DeviceSelector);
