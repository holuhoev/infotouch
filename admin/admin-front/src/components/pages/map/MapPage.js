import React, { Component } from 'react'
import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import { find, isNil, propEq } from "ramda";
import { Button, Dropdown, Menu, Spin, message, Icon, Switch } from "antd";

import {
    cancelCreatedPoints,
    createPoint,
    loadBuildingMap,
    saveCreatedPoints,
    undo,
    redo,
    addEdge,
    saveCreatedEdges,
    cancelCreatedEdges,
    changeSelectedPoint,
    changeSelectedElement, toggleEditButton
} from "../../../store/reducers/map";
import {
    selectCurrentSchemeEdges,
    selectCurrentSchemeElements, selectIsEmptyMap,
    selectPoints
} from "../../../store/selectors/map";
import './MapPage.scss'

import SchemeMenu from "./scheme-menu/SchemeMenu";
import PointInfo from "./point-info/PointInfo";
import BuildingSelector from "../../common/building/BuildingSelector";
import { loadBuildings } from "../../../store/reducers/buildings";
import Empty from "../../common/empty/Empty";
import { loadServices } from "../../../store/reducers/services";
import { loadDevices } from "../../../store/reducers/devices";
import ServiceIcon from "./service-icon/ServiceIcon";
import SchemeElement from "./scheme-element/SchemeElement";
import { loadUnits } from "../../../store/reducers/units";
import SchemeElementInfo from "./scheme-element-info/SchemeElementInfo";

const ButtonGroup = Button.Group;

const MODE = {
    NONE:       'NONE',
    ADD_POINT:  'ADD_POINT',
    ADD_EDGE:   'ADD_EDGE',
    ADD_STAIRS: 'ADD_STAIRS'
};

class MapPage extends Component {

    componentDidMount() {
        this.props.loadBuildings();
        this.props.loadBuildingMap();
        this.props.loadDevices();
    }

    constructor(props) {
        super(props);

        this.state = {
            mode:             MODE.NONE,
            startEdgePointId: null,
        }
    }

    moveAddingPoint = event => {
        if (this.created_point) {
            const [ x, y ] = this.getSvgCursorCoordinates(event);

            this.created_point.setAttribute('cx', x);
            this.created_point.setAttribute('cy', y);
        }
    };

    drawEdge = event => {
        if (this.drawing_edge) {
            const [ x, y ] = this.getSvgCursorCoordinates(event);

            this.drawing_edge.setAttribute('x2', x);
            this.drawing_edge.setAttribute('y2', y);
        }
    };

    getSvgCursorCoordinates = event => {
        let cursorPoint = this.svgElem.createSVGPoint();

        cursorPoint.x = event.clientX;
        cursorPoint.y = event.clientY;

        cursorPoint = cursorPoint.matrixTransform(this.svgElem.getScreenCTM().inverse());

        return [ cursorPoint.x.toString(), cursorPoint.y.toString() ]
    };

    startDrawingPoints = () => {
        this.svgElem.addEventListener('mousemove', this.moveAddingPoint);
        this.setState({ mode: MODE.ADD_POINT });
    };

    cancelDrawingPoint = () => {
        this.svgElem.removeEventListener('mousemove', this.moveAddingPoint);
        this.props.cancelCreatedPoints();
        this.setState({ mode: MODE.NONE });
    };

    cancelDrawingEdges = () => {
        this.svgElem.removeEventListener('mousemove', this.moveAddingPoint);
        this.props.cancelCreatedEdges();
        this.setState({ mode: MODE.NONE, startEdgePointId: null });
    };

    cancelAddStairs = () => {
        this.setState({ mode: MODE.NONE, startEdgePointId: null });
    };

    cancel = () => {
        const { mode } = this.state;

        switch (mode) {
            case MODE.ADD_EDGE:
                this.cancelDrawingEdges();
                break;
            case MODE.ADD_POINT:
                this.cancelDrawingPoint();
                break;
            case MODE.ADD_STAIRS:
                this.cancelAddStairs();
                break;
            default:
                break;
        }
    };

    startDrawingEdges = () => {
        this.setState({ mode: MODE.ADD_EDGE });
    };

    startDrawingStairs = () => {
        this.setState({ mode: MODE.ADD_STAIRS });
    };

    createPoint = () => {
        const x = this.created_point.getAttribute('cx');
        const y = this.created_point.getAttribute('cy');

        this.props.createPoint({ x: parseInt(x, 10), y: parseInt(y, 10) });
    };

    saveAll = () => {
        const { mode } = this.state;

        if (mode === MODE.ADD_POINT) {
            this.svgElem.removeEventListener('mousemove', this.moveAddingPoint);
            this.props.saveCreatedPoints();
        } else if (mode === MODE.ADD_EDGE) {
            this.svgElem.removeEventListener('mousemove', this.drawEdge);
            this.props.saveCreatedEdges();
        } else if (mode === MODE.ADD_STAIRS) {
            this.props.saveCreatedEdges();
        }


        this.setState({ mode: MODE.NONE });
    };

    undoCreate = e => {
        this.props.undo()
    };

    redoCreate = e => {
        this.props.redo()
    };

    onElementClick = element => e => {
        const { mode }   = this.state;
        const { isEdit } = this.props;

        if (isEdit) {
            switch (mode) {
                case MODE.NONE:
                    this.props.changeSelectedElement(element.id);
                    break;
                default:
                    break;
            }
        }
    };

    onPointClick = point => e => {
        const { mode } = this.state;

        switch (mode) {
            case MODE.ADD_EDGE:
                this.pointClickWithDrawingEdge(point);
                break;
            case MODE.ADD_STAIRS:
                this.pointClickWithDrawingStair(point);
                break;
            case MODE.NONE:
                this.props.changeSelectedPoint(point.id);
                break;
            default:
                break;
        }
    };

    pointClickWithDrawingEdge = point => {
        if (this.selectingFirstPoint) {
            this.svgElem.addEventListener('mousemove', this.drawEdge);
            this.setState({ startEdgePointId: point.id })
        } else if (this.isDrawingEdge) {
            this.endDrawEdge(point)
        }
    };

    pointClickWithDrawingStair = point => {
        if (this.selectingFirstPoint) {
            this.setState({ startEdgePointId: point.id })
        } else if (this.isDrawingStair) {
            message.info("Добавлено");
            this.endDrawEdge(point)
        }
    };

    endDrawEdge = secondPoint => {
        const { startEdgePointId } = this.state;
        const { id }               = secondPoint;

        if (id !== startEdgePointId) {
            this.svgElem.removeEventListener('mousemove', this.drawEdge);
            this.props.addEdge([ startEdgePointId, id ]);
            this.setState({ startEdgePointId: null });
        }
    };

    get selectingFirstPoint() {
        const { startEdgePointId } = this.state;

        return this.modeEdgeOrStair && isNil(startEdgePointId)
    }

    get isDrawingEdge() {
        const { startEdgePointId, mode } = this.state;

        return mode === MODE.ADD_EDGE && !isNil(startEdgePointId)
    }


    get isDrawingStair() {
        const { startEdgePointId, mode } = this.state;

        return mode === MODE.ADD_STAIRS && !isNil(startEdgePointId)
    }

    get modeEdgeOrStair() {
        const { mode } = this.state;


        return mode === MODE.ADD_EDGE
            || mode === MODE.ADD_STAIRS;
    }


    renderPoint = (point, index) => {
        const active                      = this.modeEdgeOrStair;
        const { selectedPointId, isEdit } = this.props;

        if (!isEdit)
            return (<ServiceIcon { ...point } key={ index }/>);

        return ([
            <circle
                className={ "map-page__point-ring" }
                onClick={ this.onPointClick(point) }
                key={ index }
                cx={ point.x }
                cy={ point.y }
                r="1.5"
                fill={ "#26c2ed" }
                stroke={ "#26c2ed" }
            />,
            (active || point.isNew || (point.id === selectedPointId)) && (
                <circle
                    className={ "map-page__point-ring" }
                    onClick={ this.onPointClick(point) }
                    key={ index + "_ring" }
                    cx={ point.x }
                    cy={ point.y }
                    r="4"
                    fill={ "none" }
                    stroke={ "#26c2ed" }
                    strokeWidth={ "1" }
                />
            ),
            (point.isInStair) && (
                <circle
                    onClick={ this.onPointClick(point) }
                    key={ index + "_stair" }
                    cx={ point.x }
                    cy={ point.y }
                    r="4"
                    fill={ "none" }
                    stroke={ "#274fed" }
                    strokeWidth={ "1" }
                />
            )
        ])
    };

    renderPoints() {
        const { points } = this.props;

        return points.map(this.renderPoint);
    }

    renderEdges() {
        const { edges, isEdit } = this.props;

        if (!isEdit)
            return null;

        return edges.map((edge, index) => (
            <line
                key={ index }
                x1={ edge.x1 }
                y1={ edge.y1 }
                x2={ edge.x2 }
                y2={ edge.y2 }
                stroke={ "#26c2ed" }
            />
        ));
    }

    renderDrawingEdge = () => {
        if (!this.isDrawingEdge)
            return null;

        return (
            <line
                id="edge"
                x1={ this.startEdgePoint.x }
                y1={ this.startEdgePoint.y }
                x2={ this.startEdgePoint.x }
                y2={ this.startEdgePoint.y }
                ref={ (e) => this.drawing_edge = e }
                stroke="black"
            />
        );
    };

    get startEdgePoint() {
        const { startEdgePointId } = this.state;
        const { points }           = this.props;

        return find(propEq('id', startEdgePointId))(points)
    }

    get addingMenu() {
        const { loading, isEmpty, isEdit } = this.props;

        const disabled = loading || isEmpty || !isEdit;

        return (
            <Menu>
                <Menu.Item disabled={ disabled } onClick={ this.startDrawingPoints }>
                    Точки
                </Menu.Item>
                <Menu.Item disabled={ disabled } onClick={ this.startDrawingEdges }>
                    Ребра
                </Menu.Item>
                <Menu.Item disabled={ disabled } onClick={ this.startDrawingStairs }>
                    Переход
                </Menu.Item>
            </Menu>
        )
    }

    afterBuildingSelect = () => {
        this.props.loadBuildingMap();
        this.props.loadServices();
        this.props.loadUnits();
    };

    render() {
        const { elements, loading, isEmpty, isEdit } = this.props;
        const { mode }                               = this.state;

        return (
            <div className={ "map-page" }>
                <div className="map-page__scheme-menu">
                    <SchemeMenu disabled={ mode === MODE.ADD_POINT }/>
                    <BuildingSelector style={ { width: 430 } } afterSelect={ this.afterBuildingSelect }/>
                </div>
                { (!isEmpty) && (
                    <div className="map-page__button-menu">

                        <Switch
                            checkedChildren={ <Icon type="edit"/> }
                            unCheckedChildren={ <Icon type="eye"/> }
                            checked={ isEdit }
                            onChange={ this.props.toggleEditButton }
                        />
                        { mode === MODE.NONE ? (
                            <Dropdown overlay={ this.addingMenu }>
                                <Button>Добавить</Button>
                            </Dropdown>
                        ) : (
                            <Button disabled={ loading || isEmpty || !isEdit } onClick={ this.cancel }>Отменить</Button>
                        ) }

                        <ButtonGroup>
                            <Button disabled={ loading || isEmpty || !isEdit }
                                    onClick={ this.saveAll }>Сохранить</Button>
                            <Button disabled={ loading || isEmpty || !isEdit }
                                    onClick={ this.undoCreate }>Отмена</Button>
                            <Button disabled={ loading || isEmpty || !isEdit }
                                    onClick={ this.redoCreate }>Вернуть</Button>
                        </ButtonGroup>
                    </div>
                ) }
                <div className="map-page__map">
                    { isEmpty && (<Empty/>) }
                    <Spin spinning={ loading }>
                        <svg
                            height="330" width="600"
                            ref={ (e) => this.svgElem = e }
                            style={ {
                                userSelect: 'none'
                            } }
                        >
                            {
                                elements.map((element, index) => (
                                    <SchemeElement
                                        key={ index }
                                        onClick={ this.onElementClick(element) }
                                        item={ element }
                                    />
                                ))
                            }
                            {
                                this.renderDrawingEdge()
                            }
                            {
                                this.renderEdges()
                            }
                            {
                                this.renderPoints()
                            }
                            { mode === MODE.ADD_POINT && (
                                <circle cx="-5" cy="-5" r="1.5"
                                        id="created_point"
                                        onClick={ this.createPoint }
                                        ref={ (e) => this.created_point = e }
                                />
                            ) }
                        </svg>
                    </Spin>
                </div>
                <PointInfo/>
                <SchemeElementInfo/>
            </div>
        )
    }

}

const mapStateToProps = (state) => {

    return {
        selectedPointId: state.map.selectedPointId,
        points:          selectPoints(state),
        elements:        selectCurrentSchemeElements(state),
        edges:           selectCurrentSchemeEdges(state),
        loading:         state.map.loading,
        isEmpty:         selectIsEmptyMap(state),
        isEdit:          state.map.isEdit
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    loadBuildingMap,
    createPoint,
    saveCreatedPoints,
    cancelCreatedPoints,
    undo,
    redo,
    addEdge,
    saveCreatedEdges,
    cancelCreatedEdges,
    loadBuildings,
    changeSelectedPoint,
    loadServices,
    changeSelectedElement,
    loadDevices,
    toggleEditButton,
    loadUnits,
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(MapPage);