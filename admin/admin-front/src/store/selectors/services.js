import { map } from "ramda";
import { SERVICE_TYPE_LABELS } from "../reducers/services";


const getServiceTypeLabel = service => SERVICE_TYPE_LABELS[ service.type ];

const getServiceForList = service => {

    return {
        ...service,
        floorLabel: service.floor ? `${ service.floor } этаж` : '',
        typeLabel:  getServiceTypeLabel(service)
    }
};

export const selectServiceList = state => {
    const services = state.services.list;
    console.log(map(getServiceForList, services));

    return map(getServiceForList, services)
};