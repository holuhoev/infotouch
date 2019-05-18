import { Select } from "antd";
import React from "react";
import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import { selectBuilding } from "../../../store/reducers/buildings";
import { selectBuildings } from "../../../store/selectors/buildings";


const Option = Select.Option;

function BuildingSelector({ buildings, selected, selectBuilding, searchBuilding, style, disabled, afterSelect }) {

    const onChange = value => {
        selectBuilding(value);
        afterSelect && afterSelect(value)
    };

    return (
        <Select
            disabled={ disabled }
            showSearch
            placeholder="Введите здание"
            value={ selected }
            onChange={ onChange }
            style={ style }
            optionFilterProp="children"
            filterOption={ (input, option) =>
                option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
        >
            { buildings.map(d => (
                <Option key={ d.id }>{ d.name }</Option>
            )) }
        </Select>
    )
}


const mapStateToProps = (state) => {

    return {
        buildings: selectBuildings(state),
        selected:  state.application.selectedBuildingId,
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    selectBuilding
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(BuildingSelector);
