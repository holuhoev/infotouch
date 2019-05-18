import React, { Component } from "react";
import { Form, Input, Modal } from "antd";
import { bindActionCreators } from "redux";
import { connect } from 'react-redux';

import { cancelEditUnit, changeUnit, saveUnit } from "../../../store/reducers/units";


class UnitModal extends Component {

    handleEdit = (e) => {
        const value = e.target.value;
        const field = e.target.name;

        if (field) {
            this.props.changeUnit({ [ field ]: value })
        }
    };

    handleSave = () => {
        this.props.saveUnit()
    };

    handleCancel = () => {
        this.props.cancelEditUnit()
    };

    render() {
        const { unit, visibleModal, saveLoading } = this.props;

        return (
            <Modal
                title={ unit.title || "Заголовок" }
                visible={ visibleModal }
                onOk={ this.handleSave }
                confirmLoading={ saveLoading }
                onCancel={ this.handleCancel }
                okText={ "Сохранить" }
                cancelText={ "Отмена" }
            >
                <Form layout="vertical" onChange={ this.handleEdit }>
                    <Form.Item>
                        <Input name={ "title" } addonBefore={ "Заголовок" } value={ unit.title }/>
                    </Form.Item>
                    <Form.Item label="Описание">
                        <Input.TextArea
                            name={ "description" }
                            autosize={ false }
                            addonBefore={ "Описание" }
                            value={ unit.description }
                        />
                    </Form.Item>
                </Form>
            </Modal>
        )
    }
}

const mapStateToProps = (state) => {

    return {
        unit:         state.units.editable,
        visibleModal: state.application.visibleModal,
        saveLoading:  state.application.saveLoading,
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    cancelEditUnit,
    saveUnit,
    changeUnit
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(UnitModal)