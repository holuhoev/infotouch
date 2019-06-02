import React from "react";
import { Fragment } from "react";

import { isElementHasLabel, isElementIsStair } from "../../../../store/reducers/map";

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


function SchemeElement({ item, onClick }) {

    return (
        <Fragment>
            <polygon
                points={ item.coordinates }
                fill={ item.isActive ? "#236481" : "#FFF" }
                stroke={ "#236481" }
                strokeWidth={ 1 }
                opacity={ 0.8 }

            />
            { isElementHasLabel(item) && (
                <text
                    style={ { cursor: 'pointer' } }
                    fill={ item.isActive ? "#FFF" : "#507b8f" }
                    x={ item.textCentroid[ 0 ] }
                    y={ item.textCentroid[ 1 ] }
                    fontSize="8"
                    textAnchor={ "middle" }
                    onClick={ onClick }
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

export default SchemeElement;