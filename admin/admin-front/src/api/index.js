import axios from 'axios';
import { propOr } from 'ramda';

import { GET_BUILDING_MAP, POST_CREATE_EDGE, POST_CREATE_POINT } from "../utils/url";


const responseData = response => propOr([], 'data', response);
const error        = (method, endpoint) => err => {
    console.error(`${ method } ${ endpoint } ${ err }`);
    return Promise.reject(err);
};

export const getBuildingMap = buildingId => {

    return axios
        .get(`${ GET_BUILDING_MAP }/${ buildingId }`)
        .then(responseData)
        .catch(error('GET', GET_BUILDING_MAP));
};

export const createNewPoints = data => {
    const { points, buildingSchemeId } = data;

    return axios
        .post(POST_CREATE_POINT, {
            buildingSchemeId,
            points
        }).then(responseData)
        .catch(error('POST', POST_CREATE_POINT));
};

export const createNewEdges = data => {
    const { edges } = data;

    return axios
        .post(POST_CREATE_EDGE, {
            edges
        }).then(responseData)
        .catch(error('POST', POST_CREATE_EDGE));
};
