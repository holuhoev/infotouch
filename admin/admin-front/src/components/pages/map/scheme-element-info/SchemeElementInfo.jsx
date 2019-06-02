import React, { Component } from 'react'
import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import { Typography, Select } from "antd";
import { selectSelectedElement } from "../../../../store/selectors/map";

const { Title } = Typography;

class SchemeElementInfo extends Component {

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
                        onChange={ this.onServiceChange }
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
        unitList:        []
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(SchemeElementInfo);