import React from 'react';
import { Select } from "antd";

import { getServiceTypeOptions } from "../../../store/selectors/services";
import { SERVICE_TYPES } from "../../../store/reducers/services";


const Option  = Select.Option;
const options = getServiceTypeOptions();

function ServiceTypeSelect({ value, onChange }) {

    return (
        <Select
            defaultValue={ SERVICE_TYPES.OTHER }
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