import React, { Component } from "react";
import { Form, Input, Modal, Spin } from "antd";
import { bindActionCreators } from "redux";
import { connect } from 'react-redux';

import { cancelEditService, changeService, saveService } from "../../../store/reducers/services";
import ServiceTypeSelect from "../../common/service/ServiceTypeSelect";


class ServiceModal extends Component {

    handleEdit = (e) => {
        const value = e.target.value;
        const field = e.target.name;

        if (field) {
            this.props.changeService({ [ field ]: value })
        }
    };

    handleSave = () => {
        this.props.saveService()
    };

    handleCancel = () => {
        this.props.cancelEditService()
    };

    handleChangeType = (value) => {
        this.props.changeService({ type: value })
    };

    render() {
        const { service, visibleModal, saveLoading } = this.props;

        return (
            <Modal
                title={ service.title || "Заголовок" }
                visible={ visibleModal }
                onOk={ this.handleSave }
                confirmLoading={ saveLoading }
                onCancel={ this.handleCancel }
                okText={ "Сохранить" }
                cancelText={ "Отмена" }
            >
                <Form layout="vertical" onChange={ this.handleEdit }>
                    <Form.Item>
                        <Input name={ "title" } addonBefore={ "Заголовок" } value={ service.title }/>
                    </Form.Item>
                    <Form.Item label={ "Тип услуги" }>
                        <ServiceTypeSelect
                            value={ service.type }
                            onChange={ this.handleChangeType }
                        />
                    </Form.Item>
                </Form>
            </Modal>
        )
    }
}

const mapStateToProps = (state) => {

    return {
        service:      state.services.editable,
        visibleModal: state.application.visibleModal,
        saveLoading:  state.application.saveLoading,
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    cancelEditService,
    saveService,
    changeService
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(ServiceModal)