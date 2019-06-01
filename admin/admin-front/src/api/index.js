import axios from 'axios';
import { propOr } from 'ramda';

import {
    GET_BUILDING_MAP,
    DEVICE,
    POST_CREATE_EDGE,
    POST_CREATE_POINT,
    SERVICE,
    BUILDING,
    SERVICE_POINT,
    UNIT,
    EVENT,
    ANNOUNCEMENT, POINT, DEVICE_POINT
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

export const putServicePoint = (data) => {

    return axios
        .put(SERVICE_POINT, data)
        .then(responseData)
        .catch(error('PUT', SERVICE_POINT))
};

export const removeServicesFromPoint = pointId => {

    return axios
        .delete(`${ SERVICE_POINT }/${ pointId }`)
        .then(responseData)
        .catch(error('DELETE', `${ SERVICE_POINT }/${ pointId }`))
};

export const getUnits = (buildingId) => {

    return axios
        .get(UNIT, {
            params: {
                buildingId
            }
        })
        .then(responseData)
        .catch(error('GET', UNIT))
};

export const putUnit = ({ title, description, buildingId }, id) => {

    return axios
        .put(`${ UNIT }/${ id }`, {
            title,
            description,
            buildingId
        })
        .then(responseData)
        .catch(error('PUT', `${ UNIT }/${ id }`))
};

export const createUnit = ({ title, description, buildingId }) => {

    return axios
        .post(UNIT, {
            title,
            description,
            buildingId
        })
        .then(responseData)
        .catch(error('POST', UNIT))
};

export const deleteUnitById = (id) => {

    return axios
        .delete(`${ UNIT }/${ id }`)
        .then(responseData)
        .catch(error('DELETE', `${ UNIT }/${ id }`))
};

export const getEvents = (deviceId) => {

    return axios
        .get(EVENT, {
            params: {
                deviceId
            }
        })
        .then(responseData)
        .catch(error('GET', EVENT))
};

export const putEvent = ({ url, deviceId }, id) => {

    return axios
        .put(`${ EVENT }/${ id }`, {
            url, deviceId
        })
        .then(responseData)
        .catch(error('PUT', `${ EVENT }/${ id }`))
};

export const createEvent = ({ url, deviceId }) => {

    return axios
        .post(EVENT, {
            url, deviceId
        })
        .then(responseData)
        .catch(error('POST', EVENT))
};

export const deleteEventById = (id) => {

    return axios
        .delete(`${ EVENT }/${ id }`)
        .then(responseData)
        .catch(error('DELETE', `${ EVENT }/${ id }`))
};

export const putAnnouncement = ({ title, deviceId }, id) => {

    return axios
        .put(`${ ANNOUNCEMENT }/${ id }`, {
            title, deviceId
        })
        .then(responseData)
        .catch(error('PUT', `${ ANNOUNCEMENT }/${ id }`))
};

export const createAnnouncement = ({ title, deviceId }) => {

    return axios
        .post(ANNOUNCEMENT, {
            title, deviceId
        })
        .then(responseData)
        .catch(error('POST', ANNOUNCEMENT))
};

export const deleteAnnouncementById = (id) => {

    return axios
        .delete(`${ ANNOUNCEMENT }/${ id }`)
        .then(responseData)
        .catch(error('DELETE', `${ ANNOUNCEMENT }/${ id }`))
};


export const getAnnouncements = (deviceId) => {

    return axios
        .get(ANNOUNCEMENT, {
            params: {
                deviceId
            }
        })
        .then(responseData)
        .catch(error('GET', ANNOUNCEMENT))
};

export const deletePointById = (pointId) => {

    return axios
        .delete(`${ POINT }/${ pointId }`)
        .then(responseData)
        .catch(error('DELETE', `${ POINT }/${ pointId }`))
};

export const putDevicePoint = (data) => {

    return axios
        .put(DEVICE_POINT, data)
        .then(responseData)
        .catch(error('PUT', DEVICE_POINT))
};

export const removeDevicesFromPoint = pointId => {

    return axios
        .delete(`${ DEVICE_POINT }/${ pointId }`)
        .then(responseData)
        .catch(error('DELETE', `${ DEVICE_POINT }/${ pointId }`))
};