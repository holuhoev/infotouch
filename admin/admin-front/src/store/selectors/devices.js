import {selectBuildingNameById} from "./buildings";

export const selectEditableDevice = (state) => {
  const { title, description, buildingId } = state.devices.editable;

  return {
    title,
    description,
    buildingName: selectBuildingNameById(state)(buildingId)
  }
};