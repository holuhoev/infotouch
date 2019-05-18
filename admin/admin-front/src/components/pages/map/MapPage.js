import React, { Component, Fragment } from 'react'
import { bindActionCreators } from "redux";
import { connect } from "react-redux";

import {
    cancelCreatedPoints,
    createPoint,
    loadBuildingMap,
    saveCreatedPoints,
    undo,
    redo, addEdge, saveCreatedEdges, cancelCreatedEdges, isElementHasLabel, isElementIsStair
} from "../../../store/reducers/map";
import {
    selectCurrentSchemeEdges,
    selectCurrentSchemeElements, selectIsEmptyMap,
    selectPoints
} from "../../../store/selectors/map";
import { find, isNil, propEq } from "ramda";
import './MapPage.scss'
import { Button, Dropdown, Menu, Spin, message } from "antd";
import SchemeMenu from "./scheme-menu/SchemeMenu";
import PointInfo from "./point-info/PointInfo";
import BuildingSelector from "../../common/building/BuildingSelector";
import { loadBuildings } from "../../../store/reducers/buildings";
import Empty from "../../common/empty/Empty";

const ButtonGroup = Button.Group;

const renderStairs = element => {
    const { lines } = element;

    if (!lines)
        return null;

    return (
        <Fragment>
            <clipPath id={ `stairsPath_${ element.id }` }>
                <polygon points={ element.coordinates }/>
            </clipPath>
            <g clipPath={ `url(#stairsPath_${ element.id })` }>
                {
                    lines.map((line, i) => (
                        <line
                            key={ i }
                            x1={ line.x1 }
                            y1={ line.y1 }
                            x2={ line.x2 }
                            y2={ line.y2 }
                            stroke={ "#236481" }
                        />
                    ))
                }
            </g>
        </Fragment>

    )
};

function Element(item) {

    return (
        <Fragment>
            <polygon
                points={ item.coordinates }
                fill="#FFF"
                stroke={ "#236481" }
                strokeWidth={ 1 }
                opacity={ 0.8 }
            />
            { isElementHasLabel(item) && (
                <text
                    fill="#507b8f"
                    x={ item.textCentroid[ 0 ] }
                    y={ item.textCentroid[ 1 ] }
                    fontSize="8"
                    textAnchor={ "middle" }
                >
                    { item.label }
                </text>
            ) }

            { isElementIsStair(item) && (
                renderStairs(item)
            ) }
        </Fragment>
    )
}

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
    }

    constructor(props) {
        super(props);

        this.state = {
            mode:             MODE.NONE,
            startEdgePointId: null,
            selectedPointId:  null
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
                this.setState({ selectedPoint: point });
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

        return mode === MODE.ADD_EDGE || mode === MODE.ADD_STAIRS;
    }


    renderPoints() {
        const { points } = this.props;
        const active     = this.modeEdgeOrStair;

        return points.map((point, index) => ([
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
            (active || point.isNew) && (
                <circle
                    className={ "map-page__point-ring" }
                    onClick={ this.onPointClick(point) }
                    key={ index + "_ring" }
                    cx={ point.x }
                    cy={ point.y }
                    r="3"
                    fill={ "none" }
                    stroke={ "#26c2ed" }
                    strokeWidth={ "0.5" }
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
        ]));
    }

    renderEdges() {
        const { edges } = this.props;

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
        const { loading } = this.props;

        return (
            <Menu>
                <Menu.Item disabled={ loading } onClick={ this.startDrawingPoints }>
                    Точки
                </Menu.Item>
                <Menu.Item disabled={ loading } onClick={ this.startDrawingEdges }>
                    Ребра
                </Menu.Item>
                <Menu.Item disabled={ loading } onClick={ this.startDrawingStairs }>
                    Лестницу
                </Menu.Item>
            </Menu>
        )
    }


    render() {
        const { elements, loading, isEmpty } = this.props;
        const { mode }                       = this.state;

        return (
            <div className={ "map-page" }>
                <div className="map-page__scheme-menu">
                    <SchemeMenu/>
                    <BuildingSelector style={ { width: 430 } } afterSelect={ this.props.loadBuildingMap }/>
                </div>

                <div className="map-page__button-menu">

                    { mode === MODE.NONE ? (
                        <Dropdown overlay={ this.addingMenu }>
                            <Button disabled={ loading } icon={ "edit" }>Добавить</Button>
                        </Dropdown>
                    ) : (
                        <Button disabled={ loading } onClick={ this.cancel }>Отменить</Button>
                    ) }

                    <ButtonGroup>
                        <Button disabled={ loading } onClick={ this.saveAll }>Сохранить</Button>
                        <Button disabled={ loading } onClick={ this.undoCreate }>Отмена</Button>
                        <Button disabled={ loading } onClick={ this.redoCreate }>Вернуть</Button>
                    </ButtonGroup>
                </div>
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
                                    <Element
                                        key={ index }
                                        { ...element }
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

                { this.state.selectedPoint && (<PointInfo point={ this.state.selectedPoint }/>) }
            </div>
        )
    }

}

const mapStateToProps = (state) => {

    return {
        points:   selectPoints(state),
        elements: selectCurrentSchemeElements(state),
        edges:    selectCurrentSchemeEdges(state),
        loading:  state.map.loading,
        isEmpty:  selectIsEmptyMap(state)
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
    loadBuildings
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(MapPage);