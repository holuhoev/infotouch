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
    redoCreatePoint
} from "../../../store/reducers/map";
import {
    selectCurrentSchemeElements,
    selectCurrentSchemePoints
} from "../../../store/selectors/map";


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
                fill="#D8D8D8"
                stroke="#979797"
                strokeWidth={ 1 }
                opacity={ 0.5 }
            />
            { isElementHasLabel(item) && (
                <text
                    fill="white"
                    fontWeight="bold"
                    x={ item.textCentroid[ 0 ] }
                    y={ item.textCentroid[ 1 ] }
                    fontSize="6"
                    textAnchor={ "middle" }
                >
                    { item.label }
                </text>
            ) }
        </Fragment>
    )
}


class MapPage extends Component {

    componentDidMount() {
        this.props.loadBuildingMap(2167)
    }

    constructor(props) {
        super(props);

        this.state = {
            isAddPointMode: false
        }
    }

    cursorPoint = event => {
        if (this.created_point) {
            let cursorPoint = this.svgElem.createSVGPoint();

            cursorPoint.x = event.clientX;
            cursorPoint.y = event.clientY;

            cursorPoint = cursorPoint.matrixTransform(this.svgElem.getScreenCTM().inverse());

            this.created_point.setAttribute('cx', cursorPoint.x.toString());
            this.created_point.setAttribute('cy', cursorPoint.y.toString());
        }
    };


    toggleAddPointMode = () => {
        const { isAddPointMode } = this.state;

        if (isAddPointMode) {
            this.svgElem.removeEventListener('mousemove', this.cursorPoint);
            this.props.cancelCreatedPoints();
        } else {
            this.svgElem.addEventListener('mousemove', this.cursorPoint);
        }

        this.setState({ isAddPointMode: !isAddPointMode });
    };

    createPoint = () => {
        const x = this.created_point.getAttribute('cx');
        const y = this.created_point.getAttribute('cy');

        this.props.createPoint({ x: parseInt(x, 10), y: parseInt(y, 10) });
    };

    saveAll = () => {
        this.svgElem.addEventListener('mousemove', this.cursorPoint);
        this.setState({ isAddPointMode: false });
        this.props.saveCreatedPoints();
    };

    undoCreate = (e) => {
        // e.preventDefault()
        this.props.undoCreatePoint()
    };

    redoCreate = e => {
        this.props.redoCreatePoint()
    };

    render() {
        const { elements, points } = this.props;
        const { isAddPointMode }   = this.state;

        return (
            <div>
                <button
                    onClick={ this.toggleAddPointMode }
                >
                    { isAddPointMode ? 'Отменить все' : 'Добавить точки' }
                </button>
                <button onClick={ this.saveAll }>Сохранить</button>
                <button onClick={ this.undoCreate }>Отмена</button>
                <button onClick={ this.redoCreate }>Вернуть</button>
                <svg
                    height="330" width="600"
                    ref={ (e) => this.svgElem = e }
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
                        points.map((point, index) => (
                            <circle
                                key={ index }
                                cx={ point.x }
                                cy={ point.y }
                                r="1.5"
                            />
                        ))
                    }
                    { isAddPointMode && (
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
        elements: selectCurrentSchemeElements(state)
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    loadBuildingMap,
    createPoint,
    saveCreatedPoints,
    cancelCreatedPoints,
    undoCreatePoint,
    redoCreatePoint
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(MapPage);