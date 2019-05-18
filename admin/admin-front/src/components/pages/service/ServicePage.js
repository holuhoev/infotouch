import React, { Component } from "react";
import { Button, Divider, Skeleton, Spin, List, Typography } from "antd";
import { bindActionCreators } from "redux";
import { connect } from "react-redux";

import { loadServices } from "../../../store/reducers/services";
import { loadBuildings } from "../../../store/reducers/buildings";
import './ServicePage.scss'
import BuildingSelector from "../../common/building/BuildingSelector";
import { selectServiceList } from "../../../store/selectors/services";


const { Title } = Typography;

class ServicePage extends Component {

    componentDidMount() {
        this.props.loadBuildings();
        this.props.loadServices()
    }

    render() {
        const { servicesLoading, services } = this.props;

        return (
            <div>
                <Spin spinning={ servicesLoading && services.length === 0 }>
                    <div className={ "service-page__button-menu" }>
                        <Button icon="plus-circle" disabled={ servicesLoading }>Добавить</Button>
                        <BuildingSelector style={ { width: 430 } } afterSelect={ this.props.loadServices }/>
                    </div>
                    <Divider orientation={ "left" }>Список услуг</Divider>
                    <div style={ {
                        overflow: 'auto',
                        height:   444
                    } }
                    >
                        <List
                            itemLayout="horizontal"
                            dataSource={ services }
                            renderItem={ service => (
                                <List.Item>
                                    <Skeleton loading={ servicesLoading } active>
                                        <List.Item.Meta
                                            avatar={ <Title level={ 4 }>{ service.id }</Title> }
                                            title={ service.title }
                                            description={ service.floorLabel + " | " + service.typeLabel }
                                        />
                                    </Skeleton>
                                </List.Item>
                            ) }
                        />
                    </div>
                </Spin>
            </div>
        )
    }
}


const mapStateToProps = (state) => {

    return {
        servicesLoading: state.application.servicesLoading,
        services:        selectServiceList(state)
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    loadServices,
    loadBuildings
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(ServicePage);