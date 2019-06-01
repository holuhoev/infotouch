import React, {Component} from "react";
import {Button, Divider, Skeleton, Spin, List, Typography} from "antd";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";

import {
  deleteAnnouncement,
  loadAnnouncements,
  openCreateAnnouncementModal,
  openEditAnnouncementModal
} from "../../../store/reducers/announcements";
import {loadDevices} from "../../../store/reducers/devices";
import './AnnouncementPage.scss'
import DeviceSelector from "../../common/device/DeviceSelector";
import AnnouncementModal from "./AnnouncementModal";
import {showDeleteConfirm} from "../../common/delete-modal/DeleteModal";
import {renderListItemActions} from "../../common/list-item-actions/ListItemActions";
import {
  selectAnnouncementList,
  selectIsCreateEnable
} from "../../../store/selectors/announcements";


class AnnouncementPage extends Component {

  componentDidMount() {
    this.props.loadDevices();
    this.props.loadAnnouncements()
  }

  onEditClick = (announcement) => () => {
    this.props.openEditAnnouncementModal(announcement)
  };

  onCreateClick = () => {
    this.props.openCreateAnnouncementModal()
  };

  onDeleteClick = ({id, title}) => () => {
    const {deleteAnnouncement} = this.props;

    showDeleteConfirm({id, onDelete: deleteAnnouncement, title: `Удалить объявление \`${title}\`?`})
  };

  render() {
    const {announcementsLoading, announcements, isCreateEnable} = this.props;

    return (
        <div>
          <Spin spinning={announcementsLoading && announcements.length === 0}>
            <div className={"announcement-page__button-menu"}>
              <Button
                  icon="plus-circle"
                  disabled={!isCreateEnable}
                  onClick={this.onCreateClick}
              >
                Добавить
              </Button>
              <DeviceSelector style={{width: 430}}
                              afterSelect={this.props.loadAnnouncements}/>
            </div>
            <Divider orientation={"left"}>Объявления</Divider>
            <div style={{
              overflow: 'auto',
              height: 444
            }}
            >
              <List
                  itemLayout="horizontal"
                  dataSource={announcements}
                  renderItem={announcement => (
                      <List.Item actions={renderListItemActions({
                        disabled: announcementsLoading,
                        item: announcement,
                        onEdit: this.onEditClick,
                        onDelete: this.onDeleteClick
                      })}>
                        <Skeleton loading={announcementsLoading} active>
                          <List.Item.Meta
                              title={announcement.title}
                          />
                        </Skeleton>
                      </List.Item>
                  )}
              />
            </div>
            <AnnouncementModal/>
          </Spin>
        </div>
    )
  }
}

const mapStateToProps = (state) => {

  return {
    announcementsLoading: state.application.announcementsLoading,
    announcements: selectAnnouncementList(state),
    isCreateEnable: selectIsCreateEnable(state)
  }
};

const mapDispatchToProps = dispatch => bindActionCreators({
  loadAnnouncements,
  loadDevices,
  deleteAnnouncement,
  openEditAnnouncementModal,
  openCreateAnnouncementModal
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(AnnouncementPage);