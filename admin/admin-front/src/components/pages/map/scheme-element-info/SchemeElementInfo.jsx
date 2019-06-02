import React, { Component } from 'react'
import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import { Typography, Select } from "antd";
import { selectSelectedElement } from "../../../../store/selectors/map";
import { selecteUnitsWithNoElement } from "../../../../store/selectors/units";
import { changeUnitElement, removeUnitElement } from "../../../../store/reducers/units";

const { Title } = Typography;

class SchemeElementInfo extends Component {

    onUnitChange =(unitId )=> {
        const { selectedElement } = this.props;
        const elementId           = selectedElement.id;

        if (!!unitId) {
            this.props.changeUnitElement({
                unitId,
                elementId
            })
        } else {
            this.props.removeUnitElement(elementId)
        }
    };

    render() {

        const { selectedElement, unitList } = this.props;

        if (!selectedElement)
            return null;

        return (
            <div className={ "point_info" }>
                <div className={ "point_info-controls" }>
                    <Title level={ 4 }>
                        { `Комната: ${ selectedElement.label }` }
                    </Title>
                    <Select
                        placeholder={ "Выберите подразделение" }
                        style={ { width: 300 } }
                        onChange={ this.onUnitChange }
                        allowClear
                        value={ selectedElement.unitId ? selectedElement.unitId.toString() : undefined }
                    >
                        { unitList.map(({ id, title }) => (
                            <Select.Option key={ id }>{ title }</Select.Option>
                        )) }
                    </Select>
                </div>
            </div>
        )
    }
}

const mapStateToProps = (state) => {

    return {
        selectedElement: selectSelectedElement(state),
        unitList:        selecteUnitsWithNoElement(state)
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    removeUnitElement,
    changeUnitElement
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(SchemeElementInfo);