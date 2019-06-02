import React, {Component} from "react";
import {Button, Divider, Skeleton, Spin, List} from "antd";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";

import {
  deleteEvent,
  loadEvents,
  openCreateEventModal,
  openEditEventModal
} from "../../../store/reducers/events";
import {loadDevices} from "../../../store/reducers/devices";
import './EventPage.scss'
import DeviceSelector from "../../common/device/DeviceSelector";
import EventModal from "./EventModal";
import {showDeleteConfirm} from "../../common/delete-modal/DeleteModal";
import {renderListItemActions} from "../../common/list-item-actions/ListItemActions";
import {
  selectEventList,
  selectIsCreateEnable
} from "../../../store/selectors/events";


class EventPage extends Component {

  componentDidMount() {
    this.props.loadDevices();
    this.props.loadEvents()
  }

  onEditClick = (event) => () => {
    this.props.openEditEventModal(event)
  };

  onCreateClick = () => {
    this.props.openCreateEventModal()
  };

  onDeleteClick = ({id,url}) => () => {
    const {deleteEvent} = this.props;

    showDeleteConfirm({id, onDelete: deleteEvent, title: `Удалить ссылку на мероприятие \`${url}\`?`})
  };

  render() {
    const {eventsLoading, events, isCreateEnable} = this.props;

    return (
        <div>
          <Spin spinning={eventsLoading && events.length === 0}>
            <div className={"event-page__button-menu"}>
              <Button
                  icon="plus-circle"
                  disabled={!isCreateEnable}
                  onClick={this.onCreateClick}
              >
                Добавить
              </Button>
              <DeviceSelector style={{width: 430}}
                              afterSelect={this.props.loadEvents}/>
            </div>
            <Divider orientation={"left"}>Список ссылок с
              мероприятиями</Divider>
            <div style={{
              overflow: 'auto',
              height: 444
            }}
            >
              <List
                  itemLayout="horizontal"
                  dataSource={events}
                  renderItem={event => (
                      <List.Item actions={renderListItemActions({
                        disabled: eventsLoading,
                        item: event,
                        onEdit: this.onEditClick,
                        onDelete: this.onDeleteClick
                      })}>
                        <Skeleton loading={eventsLoading} active>
                          <List.Item.Meta
                              // avatar={(
                              //     <Title level={4}>
                              //
                              //     </Title>
                              // )}
                              title={<a style={{
                                color: 'black',
                                fontSize: '120%'
                                // textDecoration: 'underline'
                              }} href={event.url}
                                        target={"_blank"}>{event.url}</a>}
                          />
                        </Skeleton>
                      </List.Item>
                  )}
              />
            </div>
            <EventModal/>
          </Spin>
        </div>
    )
  }
}

const mapStateToProps = (state) => {

  return {
    eventsLoading: state.application.eventsLoading,
    events: selectEventList(state),
    isCreateEnable: selectIsCreateEnable(state)
  }
};

const mapDispatchToProps = dispatch => bindActionCreators({
  loadEvents,
  loadDevices,
  deleteEvent,
  openEditEventModal,
  openCreateEventModal
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(EventPage);