import React, { Component } from 'react'
import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import { loadBuildingMap } from "../../../store/reducers/map";

class MapPage extends Component {

    componentDidMount() {
        this.props.loadMap(2167)
    }

    render() {
        const { map } = this.props;

        return (
            <div>
                Map page
                <div>
                    Edges count: { map.data.edges ? map.data.edges.length : 0 }
                </div>
            </div>
        )
    }
}

const mapStateToProps = (state) => {

    return {
        map: state.map
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    loadMap: loadBuildingMap
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(MapPage);