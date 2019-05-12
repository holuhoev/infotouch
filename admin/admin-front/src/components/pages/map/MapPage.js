import React, { Component, Fragment } from 'react'
import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import { loadBuildingMap, MAP_ELEMENTS_TYPES } from "../../../store/reducers/map";
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
        this.props.loadMap(2167)
    }

    render() {
        const { elements, points } = this.props;

        return (
            <div>
                <svg height="600" width="600">
                    {
                        elements.map((element, index) => (
                            <Element
                                key={ index }
                                { ...element }
                            />
                        ))
                    }
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
    loadMap: loadBuildingMap
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(MapPage);