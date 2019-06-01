import React, { Component } from "react";
import { Button, Divider, Skeleton, Spin, List, Typography } from "antd";
import { bindActionCreators } from "redux";
import { connect } from "react-redux";

import {
    deleteService,
    loadServices,
    openCreateServiceModal,
    openEditServiceModal
} from "../../../store/reducers/services";
import { loadBuildings } from "../../../store/reducers/buildings";
import './ServicePage.scss'
import BuildingSelector from "../../common/building/BuildingSelector";
import { selectIsCreateEnable, selectServiceList } from "../../../store/selectors/services";
import ServiceModal from "./ServiceModal";
import { showDeleteConfirm } from "../../common/delete-modal/DeleteModal";
import { renderListItemActions } from "../../common/list-item-actions/ListItemActions";


const { Title } = Typography;

class ServicePage extends Component {

    componentDidMount() {
        this.props.loadBuildings();
        this.props.loadServices()
    }

    onEditClick = (service) => () => {
        this.props.openEditServiceModal(service)
    };

    onCreateClick = () => {
        this.props.openCreateServiceModal()
    };

    onDeleteClick = ({ id,title }) => () => {
        const { deleteService } = this.props;

        showDeleteConfirm({ id, onDelete: deleteService, title: `Удалить услугу \`${title}\` ?` })
    };


    render() {
        const { servicesLoading, services, isCreateEnable } = this.props;

        return (
            <div>
                <Spin spinning={ servicesLoading && services.length === 0 }>
                    <div className={ "service-page__button-menu" }>
                        <Button
                            icon="plus-circle"
                            disabled={ !isCreateEnable }
                            onClick={ this.onCreateClick }
                        >
                            Добавить
                        </Button>
                        <BuildingSelector style={ { width: 430 } } afterSelect={ this.props.loadServices }/>
                    </div>
                    <Divider orientation={ "left" }>Список услуг</Divider>
                    <div style={ {
                        overflow: 'auto',
                        height:   390
                    } }
                    >
                        <List
                            itemLayout="horizontal"
                            dataSource={ services }
                            renderItem={ service => (
                                <List.Item actions={ renderListItemActions({
                                    disabled: servicesLoading,
                                    item:     service,
                                    onEdit:   this.onEditClick,
                                    onDelete: this.onDeleteClick
                                }) }>
                                    <Skeleton loading={ servicesLoading } active>
                                        <List.Item.Meta
                                            // avatar={ <Title level={ 4 }>{ service.id }</Title> }
                                            title={ service.title }
                                            description={ service.floorLabel
                                            + " | "
                                            + service.typeLabel
                                            + " | "
                                            + service.buildingName
                                            }
                                        />
                                    </Skeleton>
                                </List.Item>
                            ) }
                        />
                    </div>
                    <ServiceModal/>
                </Spin>
            </div>
        )
    }
}


const mapStateToProps = (state) => {

    return {
        servicesLoading: state.application.servicesLoading,
        services:        selectServiceList(state),
        isCreateEnable:  selectIsCreateEnable(state)
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    loadServices,
    loadBuildings,
    deleteService,
    openEditServiceModal,
    openCreateServiceModal
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(ServicePage);