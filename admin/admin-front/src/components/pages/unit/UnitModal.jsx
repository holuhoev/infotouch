import React, { Component } from "react";
import { Form, Input, message, Modal } from "antd";
import { bindActionCreators } from "redux";
import { connect } from 'react-redux';

import { cancelEditUnit, changeUnit, saveUnit } from "../../../store/reducers/units";
import { isEmpty } from "ramda";


class UnitModal extends Component {

    handleEdit = (e) => {
        const value = e.target.value;
        const field = e.target.name;

        if (field) {
            this.props.changeUnit({ [ field ]: value })
        }
    };

    get requireValidatedData() {
        const { unit }  = this.props;
        const { title } = unit;


        if (isEmpty(title)) {
            message.warn("Наименование должно быть заполнено");
            return false;
        }

        return true;
    }

    handleSave = () => {
        this.requireValidatedData && this.props.saveUnit()
    };

    handleCancel = () => {
        this.props.cancelEditUnit()
    };

    render() {
        const { unit, visibleModal, saveLoading } = this.props;

        return (
            <Modal
                title={ unit.title || "Подразделение" }
                visible={ visibleModal }
                onOk={ this.handleSave }
                confirmLoading={ saveLoading }
                onCancel={ this.handleCancel }
                okText={ "Сохранить" }
                cancelText={ "Отмена" }
            >
                <Form layout="vertical" onChange={ this.handleEdit }>
                    <Form.Item>
                        <Input name={ "title" } addonBefore={ "Наименование" } value={ unit.title }/>
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