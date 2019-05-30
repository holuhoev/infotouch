import React, { Component } from "react";
import { Form, Input, message, Modal } from "antd";
import { bindActionCreators } from "redux";
import { connect } from 'react-redux';

import { cancelEditAnnouncement, changeAnnouncement, saveAnnouncement } from "../../../store/reducers/announcements";
import { isEmpty } from "ramda";


class AnnouncementModal extends Component {

    handleEdit = (e) => {
        const value = e.target.value;
        const field = e.target.name;

        if (field) {
            this.props.changeAnnouncement({ [ field ]: value })
        }
    };

    get requireValidatedData() {
        const { announcement } = this.props;
        const { title }   = announcement;


        if (isEmpty(title)) {
            message.warn("Текст объявления должен быть заполнен");
            return false;
        }

        return true;
    }

    handleSave = () => {
        this.requireValidatedData && this.props.saveAnnouncement()
    };

    handleCancel = () => {
        this.props.cancelEditAnnouncement()
    };

    render() {
        const { announcement, visibleModal, saveLoading } = this.props;

        return (
            <Modal
                title={ "Объявление" }
                visible={ visibleModal }
                onOk={ this.handleSave }
                confirmLoading={ saveLoading }
                onCancel={ this.handleCancel }
                okText={ "Сохранить" }
                cancelText={ "Отмена" }
            >
                <Form layout="vertical" onChange={ this.handleEdit }>
                    <Form.Item>
                        <Form.Item label="Текст объявления">
                            <Input.TextArea
                                name={ "title" }
                                autosize={ false }
                                addonBefore={ "Описание" }
                                value={announcement.title }
                            />
                        </Form.Item>
                    </Form.Item>
                </Form>
            </Modal>
        )
    }
}

const mapStateToProps = (state) => {

    return {
        announcement:        state.announcements.editable,
        visibleModal: state.application.visibleModal,
        saveLoading:  state.application.saveLoading,
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    cancelEditAnnouncement,
    saveAnnouncement,
    changeAnnouncement
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(AnnouncementModal)