import React, { Component } from "react";

import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import { Button, Divider, List, Skeleton, Spin, Typography } from "antd";

import {
    createDevice,
    deleteDevice,
    loadDeviceById,
    loadDevices
} from "../../../store/reducers/devices";
import DeviceModal from "./DeviceModal";
import { showDeleteConfirm } from "../../common/delete-modal/DeleteModal";
import { renderListItemActions } from "../../common/list-item-actions/ListItemActions";
import {loadBuildings} from "../../../store/reducers/buildings";

const { Title } = Typography;

class DevicePage extends Component {

    componentDidMount() {
        this.props.loadBuildings();
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

        showDeleteConfirm({ id, onDelete: deleteDevice })
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
                            <List.Item actions={ renderListItemActions({
                                disabled: listLoading,
                                item:     device,
                                onEdit:   this.onEditClick,
                                onDelete: this.onDeleteClick
                            }) }>
                                <Skeleton loading={ listLoading } active>
                                    <List.Item.Meta
                                        // avatar={ <Title level={ 4 }>{ device.id }</Title> }
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
    createDevice,
    deleteDevice,
    loadBuildings
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(DevicePage);