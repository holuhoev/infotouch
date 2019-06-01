import React from "react";
import { Button } from "antd";


export const renderListItemActions = ({ disabled, onEdit, onDelete, item }) => {

    return [ (
        <Button
            disabled={ disabled }
            type={ "link" }
            icon={ "edit" }
            onClick={ onEdit(item) }
        >
            Редактировать
        </Button>
    ), (
        <Button
            disabled={ disabled }
            type={ "link" }
            icon={ "delete" }
            onClick={ onDelete(item) }
        />
    ) ]
};
