import React, { Component, Fragment } from 'react'
import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import {
    cancelCreatedPoints,
    createPoint,
    loadBuildingMap,
    MAP_ELEMENTS_TYPES,
    saveCreatedPoints
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
        let cursorPoint = this.svgElem.createSVGPoint();

        cursorPoint.x = event.clientX;
        cursorPoint.y = event.clientY;

        cursorPoint = cursorPoint.matrixTransform(this.svgElem.getScreenCTM().inverse());

        this.created_point.setAttribute('cx', cursorPoint.x.toString());
        this.created_point.setAttribute('cy', cursorPoint.y.toString());
    };


    addPointModeOn = () => {
        this.setState({ isAddPointMode: true });
        this.svgElem.addEventListener('mousemove', this.cursorPoint)
    };

    cancelAddPointMode = () => {
        this.setState({ isAddPointMode: false });
        this.svgElem.removeEventListener('mousemove', this.cursorPoint);
        this.props.cancelCreatedPoints();
    };

    createPoint = () => {
        const x = this.created_point.getAttribute('cx');
        const y = this.created_point.getAttribute('cy');

        this.props.createPoint({ x, y });
    };

    saveAll = () => {
        this.cancelAddPointMode();
        this.props.saveCreatedPoints();
    };

    render() {
        const { elements, points } = this.props;
        const { isAddPointMode }   = this.state;

        return (
            <div>
                <button onClick={ this.addPointModeOn }>Добавить точки</button>
                <button onClick={ this.cancelAddPointMode }>Отмена</button>
                <button onClick={ this.saveAll }>Сохранить</button>
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
    cancelCreatedPoints
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(MapPage);