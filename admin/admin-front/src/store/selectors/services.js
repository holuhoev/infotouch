import { filter, indexBy, map, mapObjIndexed, prop, values } from "ramda";
import { SERVICE_TYPE_LABELS } from "../reducers/services";
import { selectBuildingNameById } from "./buildings";
import { findIdByPointId, hasPoint } from "../../utils/common";


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

export const selectServicesForMap = state => {
    const services = state.services.list;

    return map(prop('type'), indexBy(prop('pointId'), filter(hasPoint, services)));
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

export const selectServiceIdByPointId = (state, pointId) => {
    return findIdByPointId(state.services.list, pointId)
};
