import React, { Component } from "react";
import { Form, Input, Modal } from "antd";
import { bindActionCreators } from "redux";
import { connect } from 'react-redux';

import { cancelEditEvent, changeEvent, saveEvent } from "../../../store/reducers/events";


class EventModal extends Component {

    handleEdit = (e) => {
        const value = e.target.value;
        const field = e.target.name;

        if (field) {
            this.props.changeEvent({ [ field ]: value })
        }
    };

    handleSave = () => {
        this.props.saveEvent()
    };

    handleCancel = () => {
        this.props.cancelEditEvent()
    };

    render() {
        const { event, visibleModal, saveLoading } = this.props;

        return (
            <Modal
                title={ event.url || "Мероприятие" }
                visible={ visibleModal }
                onOk={ this.handleSave }
                confirmLoading={ saveLoading }
                onCancel={ this.handleCancel }
                okText={ "Сохранить" }
                cancelText={ "Отмена" }
            >
                <Form layout="vertical" onChange={ this.handleEdit }>
                    <Form.Item>
                        <Input name={ "url" } addonBefore={ "Ссылка" } value={ event.url }/>
                    </Form.Item>
                </Form>
            </Modal>
        )
    }
}

const mapStateToProps = (state) => {

    return {
        event:        state.events.editable,
        visibleModal: state.application.visibleModal,
        saveLoading:  state.application.saveLoading,
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    cancelEditEvent,
    saveEvent,
    changeEvent
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(EventModal)