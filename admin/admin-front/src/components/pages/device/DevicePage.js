import React, { Component } from "react";

import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import { cancelEditDevice, editDevice, loadDeviceById, loadDevices, saveDevice } from "../../../store/reducers/devices";
import { Button, Form, Input, List, Modal, Skeleton, Spin, Typography } from "antd";

const { Title } = Typography;

class DevicePage extends Component {

    componentDidMount() {
        this.props.loadDevices();
    }

    onEdit = (device) => () => {
        this.props.loadDeviceById(device)
    };

    handleEdit = (e) => {
        const value = e.target.value;
        const field = e.target.name;

        if (field) {
            this.props.editDevice({ [ field ]: value })
        }
    };

    handleSave = () => {
        this.props.saveDevice()
    };

    handleCancel = () => {
        this.props.cancelEditDevice()
    };

    renderDeviceAction = (device) => {
        const { listLoading } = this.props;

        return [ (
            <Button
                disabled={ listLoading }
                type={ "link" }
                icon={ "edit" }
                onClick={ this.onEdit(device) }
            >
                Редактировать
            </Button>
        ) ]
    };

    render() {
        const { devices, listLoading, editableDevice, visibleModal, oneLoading, saveLoading } = this.props;


        console.log(visibleModal);
        return (
            <Spin spinning={ listLoading && devices.length === 0 }>
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
                </div>
                <Modal
                    title={ editableDevice.title }
                    visible={ visibleModal }
                    onOk={ this.handleSave }
                    confirmLoading={ saveLoading }
                    onCancel={ this.handleCancel }
                    okText={ "Сохранить" }
                    okButtonProps={ {
                        disabled: oneLoading
                    } }
                >
                    <Spin spinning={ oneLoading }>
                        <Form layout="vertical" onChange={ this.handleEdit }>
                            <Form.Item>
                                <Input name={ "title" } addonBefore={ "Заголовок" } value={ editableDevice.title }/>
                            </Form.Item>
                            <Form.Item label="Описание">
                                <Input.TextArea
                                    name={ "description" }
                                    autosize={ false }
                                    addonBefore={ "Описание" }
                                    value={ editableDevice.description }
                                />
                            </Form.Item>
                            <Form.Item>
                                <Input
                                    name={ "buildingId" }
                                    style={ {
                                        paddingTop: 10
                                    } }
                                    addonBefore={ "ID здания" }
                                    value={ editableDevice.buildingId }
                                />
                            </Form.Item>
                        </Form>
                    </Spin>
                </Modal>
            </Spin>
        )
    }
}


const mapStateToProps = (state) => {

    return {
        devices:        state.devices.list,
        listLoading:    state.application.listLoading,
        oneLoading:     state.application.oneLoading,
        editableDevice: state.devices.editable,
        visibleModal:   state.application.visibleModal,
        saveLoading:    state.application.saveLoading,
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    loadDevices,
    loadDeviceById,
    cancelEditDevice,
    saveDevice,
    editDevice
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(DevicePage);