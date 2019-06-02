import React, { Component } from "react";
import { Button, Divider, Skeleton, Spin, List } from "antd";
import { bindActionCreators } from "redux";
import { connect } from "react-redux";

import {
    deleteUnit,
    loadUnits,
    openCreateUnitModal,
    openEditUnitModal
} from "../../../store/reducers/units";
import { loadBuildings } from "../../../store/reducers/buildings";
import './UnitPage.scss'
import BuildingSelector from "../../common/building/BuildingSelector";
import { selectIsCreateEnable, selectUnitList } from "../../../store/selectors/units";
import UnitModal from "./UnitModal";
import { showDeleteConfirm } from "../../common/delete-modal/DeleteModal";
import { renderListItemActions } from "../../common/list-item-actions/ListItemActions";


class UnitPage extends Component {

    componentDidMount() {
        this.props.loadBuildings();
        this.props.loadUnits()
    }

    onEditClick = (unit) => () => {
        this.props.openEditUnitModal(unit)
    };

    onCreateClick = () => {
        this.props.openCreateUnitModal()
    };

    onDeleteClick = ({ id,title }) => () => {
        const { deleteUnit } = this.props;

        showDeleteConfirm({ id, onDelete: deleteUnit, title: `Удалить устройство \`${title}\`` })
    };


    render() {
        const { unitsLoading, units, isCreateEnable } = this.props;

        return (
            <div>
                <Spin spinning={ unitsLoading && units.length === 0 }>
                    <div className={ "unit-page__button-menu" }>
                        <Button
                            icon="plus-circle"
                            disabled={ !isCreateEnable }
                            onClick={ this.onCreateClick }
                        >
                            Добавить
                        </Button>
                        <BuildingSelector style={ { width: 430 } } afterSelect={ this.props.loadUnits }/>
                    </div>
                    <Divider orientation={ "left" }>Список подразделений</Divider>
                    <div style={ {
                        overflow: 'auto',
                        height:   444
                    } }
                    >
                        <List
                            itemLayout="horizontal"
                            dataSource={ units }
                            renderItem={ unit => (
                                <List.Item actions={ renderListItemActions({
                                    disabled: unitsLoading,
                                    item:     unit,
                                    onEdit:   this.onEditClick,
                                    onDelete: this.onDeleteClick
                                }) }>
                                    <Skeleton loading={ unitsLoading } active>
                                        <List.Item.Meta
                                            // avatar={ <Title level={ 4 }>{ unit.id }</Title> }
                                            title={ unit.title }
                                            description={ unit.floorLabel
                                            + " | "
                                            + unit.buildingName
                                            }
                                        />
                                    </Skeleton>
                                </List.Item>
                            ) }
                        />
                    </div>
                    <UnitModal/>
                </Spin>
            </div>
        )
    }
}


const mapStateToProps = (state) => {

    return {
        unitsLoading:   state.application.unitsLoading,
        units:          selectUnitList(state),
        isCreateEnable: selectIsCreateEnable(state)
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    loadUnits,
    loadBuildings,
    deleteUnit,
    openEditUnitModal,
    openCreateUnitModal
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(UnitPage);