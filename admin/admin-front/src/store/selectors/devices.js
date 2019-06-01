import { filter } from 'ramda'
import { selectBuildingNameById } from "./buildings";
import { findIdByPointId } from "../../utils/common";

export const selectEditableDevice = (state) => {
    const { title, description, buildingId } = state.devices.editable;

    return {
        title,
        description,
        buildingName: selectBuildingNameById(state)(buildingId)
    }
};

export const selectDevicesWithNoPoint = (state) => {
    const deviceList = state.devices.list;
    const selectedPointId = state.map.selectedPointId;

    return filter(s => (!s.pointId || s.pointId === selectedPointId), deviceList)
};

export const selectDeviceIdByPointId = (state, pointId) => {
    return findIdByPointId(state.devices.list, pointId)
};

