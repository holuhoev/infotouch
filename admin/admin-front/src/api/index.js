import axios from 'axios';
import { propOr } from 'ramda';

import {
    GET_BUILDING_MAP,
    DEVICE,
    POST_CREATE_EDGE,
    POST_CREATE_POINT,
    SERVICE, BUILDING
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

export const getDevices = (searchString = '') => {

    return axios
        .get(DEVICE, {
            params: {
                searchString
            }
        })
        .then(responseData)
        .catch(error('GET', DEVICE))
};

export const getDeviceById = (id) => {

    return axios
        .get(`${ DEVICE }/${ id }`)
        .then(responseData)
        .catch(error('GET', `${ DEVICE }/${ id }`))
};

export const putDevice = ({ title, description }, id) => {

    return axios
        .put(`${ DEVICE }/${ id }`, {
            title,
            description
        })
        .then(responseData)
        .catch(error('PUT', `${ DEVICE }/${ id }`))
};

export const createDevice = ({ title, description }) => {

    return axios
        .post(DEVICE, {
            title,
            description
        })
        .then(responseData)
        .catch(error('POST', DEVICE))
};

export const deleteDeviceById = (id) => {

    return axios
        .delete(`${ DEVICE }/${ id }`)
        .then(responseData)
        .catch(error('DELETE', `${ DEVICE }/${ id }`))
};

export const getServices = (buildingId) => {

    return axios
        .get(SERVICE, {
            params: {
                buildingId
            }
        })
        .then(responseData)
        .catch(error('GET', SERVICE))
};

export const getBuildings = () => {

    return axios
        .get(BUILDING)
        .then(responseData)
        .catch(error('GET', BUILDING))
};

export const putService = ({ title, type, gpsX, gpsY, buildingId }, id) => {

    return axios
        .put(`${ SERVICE }/${ id }`, {
            title,
            type,
            gpsX,
            gpsY,
            buildingId
        })
        .then(responseData)
        .catch(error('PUT', `${ SERVICE }/${ id }`))
};

export const createService = ({ title, type, gpsX, gpsY, buildingId }) => {

    return axios
        .post(SERVICE, {
            title,
            type,
            gpsX,
            gpsY,
            buildingId
        })
        .then(responseData)
        .catch(error('POST', SERVICE))
};

export const deleteServiceById = (id) => {

    return axios
        .delete(`${ SERVICE }/${ id }`)
        .then(responseData)
        .catch(error('DELETE', `${ SERVICE }/${ id }`))
};