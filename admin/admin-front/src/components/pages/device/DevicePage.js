import React, { Component } from "react";

import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import {
    createDevice,
    deleteDevice,
    loadDeviceById,
    loadDevices
} from "../../../store/reducers/devices";
import { Button, Divider, List, Skeleton, Spin, Typography } from "antd";
import DeviceModal from "./DeviceModal";
import  { showDeleteConfirm } from "./DeleteModal";

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

    onDeleteClick = ({ id }) => () => {
        const { deleteDevice } = this.props;

        showDeleteConfirm({ id, deleteDevice })
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
        ), (
            <Button
                disabled={ listLoading }
                type={ "danger" }
                icon={ "delete" }
                onClick={ this.onDeleteClick(device) }
            />
        ) ]
    };

    render() {
        const { devices, listLoading } = this.props;

        return (
            <Spin spinning={ listLoading && devices.length === 0 }>
                <Button icon="plus-circle" onClick={ this.onCreateClick }>Добавить</Button>
                <Divider orientation={ "left" }>Список устройств</Divider>
                <div style={ {
                    overflow: 'auto',
                    height:   444
                } }
                >
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
                    {/*<DeleteModal/>*/}
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
    createDevice,
    deleteDevice
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(DevicePage);