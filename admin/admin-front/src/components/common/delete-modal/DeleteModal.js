import React from 'react';
import { Modal } from "antd";

const confirm      = Modal.confirm;
const titleDefault = 'Вы уверены, что хотите удалить этот объект?';

export function showDeleteConfirm({
                                      onDelete,
                                      id,
                                      title
                                  }) {
    confirm({
        title:      title || titleDefault,
        content:    `ID - ${ id }`,
        okText:     'Да',
        okType:     'danger',
        cancelText: 'Нет',
        onOk() {
            onDelete(id);
        },
        onCancel() {
        },
    });
}