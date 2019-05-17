import React from 'react'

function PointInfo({ point }) {

    return (
        <div>
            { `Выбрана точка ${ point.x }, ${ point.y }. \nID: ${ point.id }` }
        </div>
    )
}

export default PointInfo