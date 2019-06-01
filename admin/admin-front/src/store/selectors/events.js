

export const selectEventList = state => {
    return state.events.list;
};

export const selectIsCreateEnable = state => !!state.application.selectedDeviceId && !state.application.eventsLoading;