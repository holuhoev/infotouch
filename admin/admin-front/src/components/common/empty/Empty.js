import React from "react";
import { Empty as AntEmpty } from "antd";

function Empty() {

    return (
        <AntEmpty image={ AntEmpty.PRESENTED_IMAGE_SIMPLE } description={ "Данных нет" }/>
    )
}

export default Empty;