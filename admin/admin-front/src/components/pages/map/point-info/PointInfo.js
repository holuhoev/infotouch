import React, { Component } from 'react'
import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import { Select } from "antd";

import { selectServicesWithNoPoint } from "../../../../store/selectors/services";
import { changeServicePoint } from "../../../../store/reducers/services";
import { selectSelectedPoint } from "../../../../store/selectors/map";
import './PointInfo.scss'


class PointInfo extends Component {

    onServiceChange = serviceId => {
        const { selectedPoint } = this.props;

        this.props.changeServicePoint({
            serviceId,
            pointId: selectedPoint.id

        })
    };

    render() {
        const { selectedPoint, isEditService, serviceList } = this.props;

        if (!selectedPoint)
            return null;

        return (
            <div className={ "point_info" }>
                <div className={ "point_info-data" }>
                    { `Выбрана точка ${ selectedPoint.x }, ${ selectedPoint.y }. ID: ${ selectedPoint.id }` }
                </div>
                { isEditService ? (
                    <Select
                        style={ { width: 300 } }
                        onChange={ this.onServiceChange }
                        value={ selectedPoint.serviceId ? selectedPoint.serviceId.toString() : null }
                    >
                        { serviceList.map(({ id, title }) => (
                            <Select.Option key={ id }>{ title }</Select.Option>
                        )) }
                    </Select>
                ) : (
                    <div className={ "point_info-data" }>
                        { `${ selectedPoint.serviceLabel }` }
                    </div>
                ) }
            </div>
        )
    }
}

const mapStateToProps = (state) => {

    return {
        selectedPoint: selectSelectedPoint(state),
        serviceList:   selectServicesWithNoPoint(state)
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    changeServicePoint
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(PointInfo);