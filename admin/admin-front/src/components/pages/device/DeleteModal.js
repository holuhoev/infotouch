import React  from 'react';
import { Modal } from "antd";

const confirm = Modal.confirm;

export function showDeleteConfirm({
                               deleteDevice,
                               id
                           }) {
    confirm({
        title:      'Вы уверены, что хотите удалить это устройство?',
        content:    `ID - ${ id }`,
        okText:     'Да',
        okType:     'danger',
        cancelText: 'Нет',
        onOk() {
            deleteDevice(id);
        },
        onCancel() {
        },
    });
}