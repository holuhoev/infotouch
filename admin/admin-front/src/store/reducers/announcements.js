// import {actionCreatorFactory, createReducer} from 'redux-from-void';
//
// export const {
//   loadAnnouncements,
//   loadAnnouncementsSuccess,
//   loadAnnouncementsFailed
// } = actionCreatorFactory();
//
// const initialState = {
//   list: [],
//   editable: {}
// };
//
// createReducer(
//     loadAnnouncementsSuccess,
//     (state, action) => {
//       return {
//         ...state,
//         list: action.payload
//       };
//     },
//    initialState
// );
//
//

import { createAction } from "../../utils/action";
import { excludeById } from "../../utils/common";


export const LOAD_ANNOUNCEMENTS         = 'admin/announcement/LOAD_ANNOUNCEMENTS';
export const LOAD_ANNOUNCEMENTS_SUCCESS = 'admin/announcement/LOAD_ANNOUNCEMENTS_SUCCESS';
export const LOAD_ANNOUNCEMENTS_FAILED  = 'admin/announcement/LOAD_ANNOUNCEMENTS_FAILED';

export const OPEN_EDIT_ANNOUNCEMENT_MODAL = 'admin/announcement/OPEN_EDIT_ANNOUNCEMENT_MODAL';
export const CANCEL_EDIT_ANNOUNCEMENT     = 'admin/announcement/CANCEL_EDIT_ANNOUNCEMENT';
export const OPEN_CREATE_ANNOUNCEMENT     = 'admin/announcement/OPEN_CREATE_ANNOUNCEMENT';

export const CHANGE_ANNOUNCEMENT = 'admin/announcement/CHANGE_ANNOUNCEMENT';

export const SAVE_ANNOUNCEMENT             = 'admin/announcement/SAVE_ANNOUNCEMENT';
export const SAVE_ANNOUNCEMENT_SUCCESS     = 'admin/announcement/SAVE_ANNOUNCEMENT_SUCCESS';
export const SAVE_NEW_ANNOUNCEMENT_SUCCESS = 'admin/announcement/SAVE_NEW_ANNOUNCEMENT_SUCCESS';
export const SAVE_ANNOUNCEMENT_FAILED      = 'admin/announcement/SAVE_ANNOUNCEMENT_FAILED';

export const DELETE_ANNOUNCEMENT         = 'admin/announcement/DELETE_ANNOUNCEMENT';
export const DELETE_ANNOUNCEMENT_SUCCESS = 'admin/announcement/DELETE_ANNOUNCEMENT_SUCCESS';
export const DELETE_ANNOUNCEMENT_FAILED  = 'admin/announcement/DELETE_ANNOUNCEMENT_FAILED';


export const openEditAnnouncementModal   = createAction(OPEN_EDIT_ANNOUNCEMENT_MODAL);
export const loadAnnouncements           = createAction(LOAD_ANNOUNCEMENTS);
export const saveAnnouncement            = createAction(SAVE_ANNOUNCEMENT);
export const deleteAnnouncement          = createAction(DELETE_ANNOUNCEMENT);
export const cancelEditAnnouncement      = createAction(CANCEL_EDIT_ANNOUNCEMENT);
export const changeAnnouncement          = createAction(CHANGE_ANNOUNCEMENT);
export const openCreateAnnouncementModal = createAction(OPEN_CREATE_ANNOUNCEMENT);

const initState = {
  list:     [],
  editable: {},
};



export const announcements = (state = initState, action = {}) => {

  switch (action.type) {

    case DELETE_ANNOUNCEMENT_SUCCESS:

      return {
        ...state,
        list: excludeById(action.payload, state.list)
      };

    case OPEN_CREATE_ANNOUNCEMENT:

      return {
        ...state,
        editable: {
          isNew: true
        }
      };

    case OPEN_EDIT_ANNOUNCEMENT_MODAL:

      return {
        ...state,
        editable: action.payload
      };

    case LOAD_ANNOUNCEMENTS_SUCCESS:

      return {
        ...state,
        list: action.payload
      };
    case CANCEL_EDIT_ANNOUNCEMENT:

      return {
        ...state,
        editable: {}
      };
    case SAVE_NEW_ANNOUNCEMENT_SUCCESS:

      return {
        ...state,
        editable: {},
        list:     [
          ...state.list,
          action.payload
        ]
      };
    case SAVE_ANNOUNCEMENT_SUCCESS:

      return {
        ...state,
        editable: {},
        list:     [
          ...state.list.map(announcement => {
            if (announcement.id === action.payload.id) {
              return action.payload;
            }
            return announcement;
          }),
        ]
      };
    case CHANGE_ANNOUNCEMENT:

      return {
        ...state,
        editable: {
          ...state.editable,
          ...action.payload
        }
      };
    default:
      return state;
  }
};
