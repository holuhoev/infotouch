import React, { Component } from 'react'
import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import { Button, Select, Typography } from "antd";

import { selectServicesWithNoPoint } from "../../../../store/selectors/services";
import { changeServicePoint, removeServicePoint } from "../../../../store/reducers/services";
import { selectSelectedPoint } from "../../../../store/selectors/map";
import './PointInfo.scss'
import { deletePoint } from "../../../../store/reducers/map";
import { selectDevicesWithNoPoint } from "../../../../store/selectors/devices";
import { changeDevicePoint, removeDevicePoint } from "../../../../store/reducers/devices";

const { Title } = Typography;

class PointInfo extends Component {

    onServiceChange = serviceId => {
        const { selectedPoint } = this.props;
        const pointId           = selectedPoint.id;

        if (!!serviceId) {
            this.props.changeServicePoint({
                serviceId,
                pointId
            })
        } else {
            this.props.removeServicePoint(pointId)
        }
    };

    onDeviceChange = deviceId => {
        const { selectedPoint } = this.props;
        const pointId           = selectedPoint.id;

        if (!!deviceId) {
            this.props.changeDevicePoint({
                deviceId,
                pointId
            })
        } else {
            this.props.removeDevicePoint(pointId)
        }
    };

    onDeleteClick = () => {
        const { selectedPoint, deletePoint } = this.props;

        deletePoint(selectedPoint.id);
    };

    render() {
        const { selectedPoint, serviceList, deviceList } = this.props;

        if (!selectedPoint)
            return null;

        console.log(deviceList);
        console.log(serviceList);

        return (
            <div className={ "point_info" }>
                <div className={ "point_info-title" }>
                    <Title level={ 4 }>
                        { `Выбрана точка х:${ selectedPoint.x }, y:${ selectedPoint.y }. ID: ${ selectedPoint.id }` }
                    </Title>
                </div>
                <div className={ "point_info-controls" }>
                    <Select
                        placeholder={ "Выберите услугу" }
                        style={ { width: 200 } }
                        onChange={ this.onServiceChange }
                        allowClear
                        value={ selectedPoint.serviceId ? selectedPoint.serviceId.toString() : undefined }
                    >
                        { serviceList.map(({ id, title }) => (
                            <Select.Option key={ id } >{ title }</Select.Option>
                        )) }
                    </Select>
                    <Select
                        placeholder={ "Выберите устройство" }
                        style={ { width: 200 } }
                        onChange={ this.onDeviceChange }
                        allowClear
                        value={ selectedPoint.deviceId ? selectedPoint.deviceId.toString() : undefined }
                    >
                        { deviceList.map(device => (
                            <Select.Option key={ device.id.toString() }>{ device.title }</Select.Option>
                        )) }
                    </Select>
                    <Button
                        onClick={ this.onDeleteClick }
                        type={ "danger" }
                    >
                        Удалить точку
                    </Button>
                </div>
            </div>
        )
    }
}

const mapStateToProps = (state) => {

    return {
        selectedPoint: selectSelectedPoint(state),
        serviceList:   selectServicesWithNoPoint(state),
        deviceList:    selectDevicesWithNoPoint(state)
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    changeServicePoint,
    removeServicePoint,
    changeDevicePoint,
    removeDevicePoint,
    deletePoint
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(PointInfo);