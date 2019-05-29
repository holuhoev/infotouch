import React, { Component } from "react";
import { Form, Input, Modal, Spin, message } from "antd";
import { bindActionCreators } from "redux";
import { connect } from 'react-redux';

import {
    cancelEditDevice,
    editDevice,
    saveDevice
} from "../../../store/reducers/devices";
import { isEmpty } from "ramda";
import {selectEditableDevice} from "../../../store/selectors/devices";


class DeviceModal extends Component {

    handleEdit = (e) => {
        const value = e.target.value;
        const field = e.target.name;

        if (field) {
            this.props.editDevice({ [ field ]: value })
        }
    };

    get requireValidatedData() {
        const { editableDevice } = this.props;
        const { title }          = editableDevice;


        if (isEmpty(title)) {
            message.warn("Наименование должно быть заполнено");
            return false;
        }

        return true;
    }

    handleSave = () => {
        this.requireValidatedData && this.props.saveDevice();
    };

    handleCancel = () => {
        this.props.cancelEditDevice()
    };


    render() {
        const { editableDevice, visibleModal, saveLoading, oneLoading } = this.props;

        return (
            <Modal
                title={ editableDevice.title || "Устройство" }
                visible={ visibleModal }
                onOk={ this.handleSave }
                confirmLoading={ saveLoading }
                onCancel={ this.handleCancel }
                okText={ "Сохранить" }
                cancelText={ "Отмена" }
                okButtonProps={ {
                    disabled: oneLoading
                } }
            >
                <Spin spinning={ oneLoading }>
                    <Form layout="vertical" onChange={ this.handleEdit }>
                        <Form.Item>
                            <Input name={ "title" } addonBefore={ "Наименование" } value={ editableDevice.title }/>
                        </Form.Item>
                        <Form.Item label="Описание">
                            <Input.TextArea
                                name={ "description" }
                                autosize={ false }
                                addonBefore={ "Описание" }
                                value={ editableDevice.description }
                            />
                        </Form.Item>
                        {editableDevice.buildingName && (
                            <Form.Item>
                                <Input
                                    disabled={ true }
                                    style={ {
                                        paddingTop: 10
                                    } }
                                    addonBefore={ "Адрес" }
                                    value={ editableDevice.buildingName }
                                />
                            </Form.Item>
                        )}
                    </Form>
                </Spin>
            </Modal>
        )
    }
}

const mapStateToProps = (state) => {

    return {
        oneLoading:     state.application.oneLoading,
        editableDevice: selectEditableDevice(state),
        visibleModal:   state.application.visibleModal,
        saveLoading:    state.application.saveLoading,
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    cancelEditDevice,
    saveDevice,
    editDevice
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(DeviceModal)