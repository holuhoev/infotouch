import React from 'react';
import { Radio } from "antd";
import { Component } from "react";
import { bindActionCreators } from "redux";
import { sortBy, prop } from 'ramda';

import {
    selectMapCurrentSchemeId,
    selectSchemes
} from "../../../../store/selectors/map";
import { connect } from "react-redux";
import { changeScheme } from "../../../../store/reducers/map";


class SchemeMenu extends Component {


    onChangeScheme = e => {
        this.props.changeScheme(e.target.value)
    };

    render() {
        const { schemes, currentSchemeId, disabled } = this.props;

        if (!schemes || schemes.length === 0)
            return null;

        return (
            <Radio.Group
                disabled={ disabled }

                value={ currentSchemeId }
                onChange={ this.onChangeScheme }
            >
                {
                    schemes.map(scheme => (
                        <Radio.Button key={ scheme.id } value={ scheme.id }>
                            { `${ scheme.floor } этаж` }
                        </Radio.Button>
                    ))
                }
            </Radio.Group>
        )
    }
}

const mapStateToProps = (state, ownProps) => {

    return {
        currentSchemeId: selectMapCurrentSchemeId(state),
        schemes:         sortBy(prop('floor'))(selectSchemes(state)),
        disabled:        state.map.loading || ownProps.disabled
    }
};


const mapDispatchToProps = dispatch => bindActionCreators({
    changeScheme
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(SchemeMenu)