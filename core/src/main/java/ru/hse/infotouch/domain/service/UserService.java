package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.datasource.DeviceDatasource;
import ru.hse.infotouch.domain.datasource.UserDatasource;
import ru.hse.infotouch.domain.dto.request.UserCreateRequest;
import ru.hse.infotouch.domain.dto.request.UserDeviceRequest;
import ru.hse.infotouch.domain.dto.request.UserUpdateRequest;
import ru.hse.infotouch.domain.models.admin.User;
import ru.hse.infotouch.domain.models.admin.relations.User2Device;
import ru.hse.infotouch.domain.repo.User2DeviceRepository;
import ru.hse.infotouch.domain.repo.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserDatasource datasource;

    private final DeviceDatasource deviceDatasource;
    private final User2DeviceRepository user2DeviceRepository;
    private final DeviceService deviceService;

    private final EntityManager em;

    public UserService(UserRepository repository,
                       UserDatasource datasource,
                       DeviceDatasource deviceDatasource,
                       User2DeviceRepository user2DeviceRepository,
                       DeviceService deviceService,
                       EntityManager em) {
        this.repository = repository;
        this.datasource = datasource;
        this.deviceDatasource = deviceDatasource;
        this.user2DeviceRepository = user2DeviceRepository;
        this.deviceService = deviceService;
        this.em = em;
    }

    public User getOneById(int id) {
        User user = this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Пользователя с id \"%d\" не существует", id)));
        user.setDevices(deviceDatasource.findAllByUserId(id));

        return user;
    }

    public List<User> findAll(String searchString, int page) {

        return datasource.findAll(searchString, page);
    }

    @Transactional
    public User create(UserCreateRequest createRequest) {
        requireExistingDevices(createRequest.getDevices());

        User toSave = User.createFromRequest(createRequest);

        User user = repository.save(toSave);
        insertDeviceRelations(user.getId(), createRequest.getDevices());

        user.setDevices(deviceDatasource.findAllByUserId(user.getId()));

        return user;
    }

    @Transactional
    public User update(int id, UserUpdateRequest updateRequest) {
        requireExistingDevices(updateRequest.getDevices());

        User user = this.getOneById(id)
                .updateFromRequest(updateRequest);

        deleteDeviceRelations(id);
        insertDeviceRelations(user.getId(), updateRequest.getDevices());

        User saved = this.repository.save(user);

        saved.setDevices(deviceDatasource.findAllByUserId(id));

        return saved;
    }

    @Transactional
    public void delete(int id) {
        final User user = this.getOneById(id);

        this.deleteDeviceRelations(id);

        this.repository.delete(user);
    }

    private void deleteDeviceRelations(int userId) {
        Query query = em.createNativeQuery("delete from user2device ut where ut.user_id = :userId ");
        query.setParameter("userId", userId).executeUpdate();
    }

    private void insertDeviceRelations(int userId, List<UserDeviceRequest> devices) {
        List<User2Device> toSave = devices.stream()
                .map(device -> User2Device.createOf(userId, device.getDeviceId(), device.getAccessRight()))
                .collect(Collectors.toList());

        user2DeviceRepository.saveAll(toSave);
    }

    private void requireExistingDevices(List<UserDeviceRequest> devices) {
        int[] deviceIds = devices.stream()
                .mapToInt(UserDeviceRequest::getDeviceId)
                .toArray();

        if (deviceService.isNotExistAll(deviceIds)) {
            throw new IllegalArgumentException("Does not all devices exist.");
        }
    }
}
