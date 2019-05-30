

export const selectAnnouncementList = state => {
  return state.announcements.list;
};

export const selectIsCreateEnable = state => !!state.application.selectedDeviceId && !state.application.announcementsLoading;