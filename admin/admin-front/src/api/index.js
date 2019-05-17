import axios from 'axios';
import { propOr } from 'ramda';

import {
    CREATE_DEVICE,
    GET_BUILDING_MAP,
    GET_DEVICES,
    POST_CREATE_EDGE,
    POST_CREATE_POINT,
    PUT_DEVICE
} from "../utils/url";


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

export const getDevices = () => {

    return axios
        .get(GET_DEVICES)
        .then(responseData)
        .catch(error('GET', GET_DEVICES))
};

export const getDeviceById = (id) => {

    return axios
        .get(`${ GET_DEVICES }/${ id }`)
        .then(responseData)
        .catch(error('GET', `${ GET_DEVICES }/${ id }`))
};

export const putDevice = ({ title, description }, id) => {

    return axios
        .put(`${ PUT_DEVICE }/${ id }`, {
            title,
            description
        })
        .then(responseData)
        .catch(error('PUT', `${ PUT_DEVICE }/${ id }`))
};

export const createDevice = ({ title, description }) => {

    return axios
        .post(CREATE_DEVICE, {
            title,
            description
        })
        .then(responseData)
        .catch(error('POST', CREATE_DEVICE))
};