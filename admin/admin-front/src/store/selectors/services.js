import { filter, find, indexBy, map, mapObjIndexed, prop, propEq, values } from "ramda";
import { SERVICE_TYPE_LABELS } from "../reducers/services";
import { selectBuildingNameById } from "./buildings";


const getServiceTypeLabel = service => SERVICE_TYPE_LABELS[ service.type ];

const getServiceForList = state => service => {
    const { buildingId, floor } = service;

    return {
        ...service,
        floorLabel:   floor ? `${ floor } этаж` : '',
        typeLabel:    getServiceTypeLabel(service) || '',
        buildingName: selectBuildingNameById(state)(buildingId) || ''
    }
};

export const selectServiceList = state => {
    const services = state.services.list;

    return map(getServiceForList(state), services)
};

export const getServiceTypeOptions = () => {
    return values(mapObjIndexed((value, key) => ({ label: value, value: key }), SERVICE_TYPE_LABELS))
};

export const selectIsCreateEnable = state => !!state.application.selectedBuildingId && !state.application.servicesLoading;

export const selectServicesWithNoPoint = state => {
    const services        = state.services.list;
    const selectedPointId = state.map.selectedPointId;

    return filter(s => (!s.pointId || s.pointId === selectedPointId), services);
};

const selectServiceByPointId = (state, pointId) => {
    return find(propEq('pointId', pointId))(state.services.list)
};

export const selectServiceIdByPointId = (state, pointId) => {
    const service = selectServiceByPointId(state, pointId);

    return service ? service.id : null
};

export const selectForSavePoints = (state) => {
    return map(prop('pointId'), indexBy(prop('id'), filter(prop('isPointEdit'), state.services.list)))
};