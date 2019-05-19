import { Select } from "antd";
import React from "react";
import { bindActionCreators } from "redux";
import { connect } from "react-redux";

import { selectDevice } from "../../../store/reducers/devices";


const Option = Select.Option;

function DeviceSelector({ devices, selected, selectDevice, searchDevice, style, disabled, afterSelect }) {

    const onChange = value => {
        selectDevice(value);
        afterSelect && afterSelect(value)
    };

    return (
        <Select
            disabled={ disabled }
            showSearch
            placeholder="Выберите устройство"
            value={ selected }
            onChange={ onChange }
            style={ style }
            optionFilterProp="children"
            filterOption={ (input, option) =>
                option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
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
        selected: state.application.selectedDeviceId,
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    selectDevice
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(DeviceSelector);
