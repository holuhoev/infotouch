import React from 'react';
import { Select } from "antd";

import { getServiceTypeOptions } from "../../../store/selectors/services";

const Option  = Select.Option;
const options = getServiceTypeOptions();

function ServiceTypeSelect({ value, onChange }) {

    return (
        <Select
            value={ value }
            onChange={ onChange }
        >
            { options.map(({ value, label }) => (
                <Option key={ value }>{ label }</Option>
            )) }
        </Select>
    );
}


export default ServiceTypeSelect;