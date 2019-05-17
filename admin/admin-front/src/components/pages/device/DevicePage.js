import React, { Component } from "react";

import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import { loadDevices } from "../../../store/reducers/devices";
import { List, Spin, Typography } from "antd";

const { Title } = Typography;

class DevicePage extends Component {

    componentDidMount() {
        this.props.loadDevices();
    }

    render() {
        const { devices, loading } = this.props;

        return (
            <Spin spinning={ loading }>
                <div style={ { minHeight: 300 } }>
                    <List
                        itemLayout="horizontal"
                        dataSource={ devices }
                        renderItem={ device => (
                            <List.Item>
                                <List.Item.Meta
                                    avatar={ <Title level={ 4 }>{ device.id }</Title> }
                                    title={ device.title }
                                    description={ device.description }
                                />
                            </List.Item>
                        ) }
                    />
                </div>
            </Spin>
        )
    }
}


const mapStateToProps = (state) => {

    return {
        devices: state.devices,
        loading: state.application.loading
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    loadDevices
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(DevicePage);