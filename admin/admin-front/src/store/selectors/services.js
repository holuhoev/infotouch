import { map } from "ramda";
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