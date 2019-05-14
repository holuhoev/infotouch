import React, { Component, Fragment } from 'react'
import { bindActionCreators } from "redux";
import { connect } from "react-redux";

import {
    cancelCreatedPoints,
    createPoint,
    loadBuildingMap,
    MAP_ELEMENTS_TYPES,
    saveCreatedPoints,
    undoCreatePoint,
    redoCreatePoint, addEdge, saveCreatedEdges, cancelCreatedEdges
} from "../../../store/reducers/map";
import {
    selectCurrentSchemeEdges,
    selectCurrentSchemeElements,
    selectCurrentSchemePoints
} from "../../../store/selectors/map";
import { find, isNil, propEq } from "ramda";


const isElementHasLabel = element => {

    return element.type === MAP_ELEMENTS_TYPES.ROOM
        && !!element.textCentroid
        && !!element.label;
};

function Element(item) {

    return (
        <Fragment>
            <polygon
                points={ item.coordinates }
                fill="#FFF"
                stroke="#236481"
                strokeWidth={ 1 }
                opacity={ 0.8 }
            />
            { isElementHasLabel(item) && (
                <text
                    fill="#507b8f"
                    // fontWeight="bold"
                    x={ item.textCentroid[ 0 ] }
                    y={ item.textCentroid[ 1 ] }
                    fontSize="8"
                    textAnchor={ "middle" }
                >
                    { item.label }
                </text>
            ) }
        </Fragment>
    )
}

const MODE = {
    NONE:      'NONE',
    ADD_POINT: 'ADD_POINT',
    ADD_EDGE:  'ADD_EDGE'
};

class MapPage extends Component {

    componentDidMount() {
        this.props.loadBuildingMap(2167)
    }

    constructor(props) {
        super(props);

        this.state = {
            mode:             MODE.NONE,
            startEdgePointId: null
        }
    }

    cursorPoint = event => {
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

    toggleAddPointMode = () => {
        const { mode } = this.state;

        if (mode === MODE.ADD_POINT) {
            this.svgElem.removeEventListener('mousemove', this.cursorPoint);
            this.props.cancelCreatedPoints();
            this.setState({ mode: MODE.NONE });
        } else {
            this.svgElem.addEventListener('mousemove', this.cursorPoint);
            this.setState({ mode: MODE.ADD_POINT });
        }
    };

    toggleAddEdgeMode = () => {
        const { mode } = this.state;

        if (mode === MODE.ADD_EDGE) {
            this.setState({ mode: MODE.NONE });
        } else {
            this.setState({ mode: MODE.ADD_EDGE });
        }
    };

    createPoint = () => {
        const x = this.created_point.getAttribute('cx');
        const y = this.created_point.getAttribute('cy');

        this.props.createPoint({ x: parseInt(x, 10), y: parseInt(y, 10) });
    };

    saveAll = () => {
        const { mode } = this.state;

        if (mode === MODE.ADD_POINT) {
            this.svgElem.removeEventListener('mousemove', this.cursorPoint);
            this.props.saveCreatedPoints();
        } else if (mode === MODE.ADD_EDGE) {
            this.svgElem.removeEventListener('mousemove', this.drawEdge);
            this.props.saveCreatedEdges();
        }


        this.setState({ mode: MODE.NONE });
    };

    undoCreate = e => {
        this.props.undoCreatePoint()
    };

    redoCreate = e => {
        this.props.redoCreatePoint()
    };

    onPointClick = point => e => {
        if (this.selectingFirstPoint) {
            this.startDrawEdge(point);
        } else if (this.isDrawingEdge) {
            this.endDrawEdge(point)
        }
    };

    startDrawEdge = point => {
        this.svgElem.addEventListener('mousemove', this.drawEdge);
        this.setState({ startEdgePointId: point.id })
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
        const { mode, startEdgePointId } = this.state;

        return mode === MODE.ADD_EDGE && isNil(startEdgePointId)
    }

    get isDrawingEdge() {
        const { mode, startEdgePointId } = this.state;

        return mode === MODE.ADD_EDGE && !isNil(startEdgePointId)
    }

    renderPoints() {
        const { points } = this.props;

        return points.map((point, index) => (
            <circle
                onClick={ this.onPointClick(point) }
                key={ index }
                cx={ point.x }
                cy={ point.y }
                r="1.5"
                fill={ "#26c2ed" }
                stroke={ "#26c2ed" }
            />
        ));
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

    render() {
        const { elements, edges } = this.props;
        const { mode }            = this.state;

        console.log(edges);

        return (
            <div>
                <button
                    onClick={ this.toggleAddPointMode }
                    disabled={ mode === MODE.ADD_EDGE }
                >
                    { mode === MODE.ADD_POINT ? 'Отменить все' : 'Добавить точки' }
                </button>
                <button
                    onClick={ this.toggleAddEdgeMode }
                    disabled={ mode === MODE.ADD_POINT }
                >
                    { mode === MODE.ADD_EDGE ? 'Отменить все' : 'Добавить ребра' }
                </button>
                <button onClick={ this.saveAll }>Сохранить</button>
                <button onClick={ this.undoCreate }>Отмена</button>
                <button onClick={ this.redoCreate }>Вернуть</button>
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
                        <circle cx="75" cy="60" r="1.5"
                                id="created_point"
                                onClick={ this.createPoint }
                                ref={ (e) => this.created_point = e }
                        />
                    ) }

                </svg>
            </div>
        )
    }
}

const mapStateToProps = (state) => {

    return {
        points:   selectCurrentSchemePoints(state),
        elements: selectCurrentSchemeElements(state),
        edges:    selectCurrentSchemeEdges(state)
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    loadBuildingMap,
    createPoint,
    saveCreatedPoints,
    cancelCreatedPoints,
    undoCreatePoint,
    redoCreatePoint,
    addEdge,
    saveCreatedEdges,
    cancelCreatedEdges
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(MapPage);