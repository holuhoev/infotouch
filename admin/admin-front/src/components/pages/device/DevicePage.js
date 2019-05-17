import React, { Component } from "react";

import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import { createDevice, loadDeviceById, loadDevices } from "../../../store/reducers/devices";
import { Button, ConfigProvider, Divider, Empty, List, Skeleton, Spin, Typography } from "antd";
import DeviceModal from "./DeviceModal";

const { Title } = Typography;

class DevicePage extends Component {

    componentDidMount() {
        this.props.loadDevices();
    }

    onEditClick = (device) => () => {
        this.props.loadDeviceById(device)
    };

    onCreateClick = () => {
        this.props.createDevice()
    };

    renderDeviceAction = (device) => {
        const { listLoading } = this.props;

        return [ (
            <Button
                disabled={ listLoading }
                type={ "link" }
                icon={ "edit" }
                onClick={ this.onEditClick(device) }
            >
                Редактировать
            </Button>
        ) ]
    };

    render() {
        const { devices, listLoading } = this.props;

        return (
            <Spin spinning={ listLoading && devices.length === 0 }>
                <Button icon="plus-circle" onClick={ this.onCreateClick }>Добавить</Button>
                <Divider orientation={ "left" }>Список устройств</Divider>
                <div style={ { minHeight: 300 } }>

                    <List
                        itemLayout="horizontal"
                        dataSource={ devices }
                        renderItem={ device => (
                            <List.Item actions={ this.renderDeviceAction(device) }>
                                <Skeleton loading={ listLoading } active>
                                    <List.Item.Meta
                                        avatar={ <Title level={ 4 }>{ device.id }</Title> }
                                        title={ device.title }
                                        description={ device.description }
                                    />
                                </Skeleton>
                            </List.Item>
                        ) }
                    />
                    <DeviceModal/>
                </div>
            </Spin>
        )
    }
}


const mapStateToProps = (state) => {

    return {
        devices:     state.devices.list,
        listLoading: state.application.listLoading,
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    loadDevices,
    loadDeviceById,
    createDevice
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(DevicePage);