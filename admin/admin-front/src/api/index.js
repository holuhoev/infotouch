import axios from 'axios';
import { propOr } from 'ramda';

import { GET_BUILDING_MAP, getPostCreatePointUrl } from "../utils/url";


const responseData = response => propOr([], 'data', response);
const error        = (method, endpoint) => err => {
    console.error(`${ method } ${ endpoint } ${ err }`);
    return Promise.reject(err);
};

export const getBuildingMap = buildingId => {
    console.log("get map: " + buildingId);

    return axios
        .get(`${ GET_BUILDING_MAP }/${ buildingId }`)
        .then(responseData)
        .catch(error('GET', GET_BUILDING_MAP));
};

export const createNewPoints = data => {
    const { points, buildingSchemeId } = data;

    return axios
        .post(getPostCreatePointUrl(buildingSchemeId), {
            data: {
                points
            }
        }).then(responseData)
        .catch(error('POST', getPostCreatePointUrl(buildingSchemeId)));
};
