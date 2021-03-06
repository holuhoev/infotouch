package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.datasource.DeviceDatasource;
import ru.hse.infotouch.domain.dto.request.DevicePointRequest;
import ru.hse.infotouch.domain.dto.request.DeviceRequest;
import ru.hse.infotouch.domain.models.admin.Device;
import ru.hse.infotouch.domain.models.admin.relations.*;
import ru.hse.infotouch.domain.models.map.BuildingScheme;
import ru.hse.infotouch.domain.models.map.SchemeElement;
import ru.hse.infotouch.domain.repo.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private final EntityManager entityManager;
    private final DeviceDatasource datasource;
    private final DeviceRepository deviceRepository;
    private final Device2NewsRepository device2NewsRepository;
    private final Device2AnnouncementRepository device2AnnouncementRepository;
    private final Device2AdRepository device2AdRepository;
    private final PointRepository pointRepository;
    private final BuildingSchemeService buildingSchemeService;
    private final SchemeElementService schemeElementService;

    public DeviceService(EntityManager entityManager,
                         DeviceDatasource datasource,
                         DeviceRepository deviceRepository,
                         Device2NewsRepository device2NewsRepository,
                         Device2AnnouncementRepository device2AnnouncementRepository,
                         Device2AdRepository device2AdRepository,
                         PointRepository pointRepository, BuildingSchemeService buildingSchemeService, SchemeElementService schemeElementService) {
        this.entityManager = entityManager;
        this.datasource = datasource;
        this.deviceRepository = deviceRepository;
        this.device2NewsRepository = device2NewsRepository;
        this.device2AnnouncementRepository = device2AnnouncementRepository;
        this.device2AdRepository = device2AdRepository;
        this.pointRepository = pointRepository;
        this.buildingSchemeService = buildingSchemeService;
        this.schemeElementService = schemeElementService;
    }


    public List<Device> findAll(String searchString, int page) {

        return datasource.findAll(searchString, page);
    }

    public Device getOneById(int id) {
        Device device = this.deviceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Информационного устройства с id \"%d\" не существует", id)));

        if (device.getPointId() != null) {
            pointRepository.findById(device.getPointId()).ifPresent(point -> {
                // replace this
                if (point.getSchemeElementId() != null) {
                    SchemeElement schemeElement = schemeElementService.getOneById(point.getSchemeElementId());
                    if (schemeElement.getBuildingSchemeId() != null) {
                        BuildingScheme buildingScheme = buildingSchemeService.getOneById(schemeElement.getBuildingSchemeId());
                        device.setBuildingId(buildingScheme.getBuildingId());
                    }
                }
            });
        }

        return device;
    }

    @Transactional
    public Device create(DeviceRequest request) {
        Device device = new Device();

//        device.setLocation(geometryFactory.createPoint(new Coordinate(request.getX(), request.getY())));

        device.updateFromRequest(request);

        return deviceRepository.save(device);
    }

    @Transactional
    public Device update(int id, DeviceRequest request) {
        Device device = this.getOneById(id);

//        device.setLocation(geometryFactory.createPoint(new Coordinate(request.getX(), request.getY())));
        device.updateFromRequest(request);

        return deviceRepository.save(device);
    }

    @Transactional
    public void delete(int id) {
        Device device = this.getOneById(id);

        deviceRepository.delete(device);
    }

    public boolean isNotExistAll(int... ids) {
        for (int id : ids) {
            if (!deviceRepository.existsById(id)) {
                return true;
            }
        }

        return false;
    }

    public void deleteAllNewsRelations(int newsId) {
        Query query = entityManager.createNativeQuery("delete from device2news tn where tn.news_id = :newsId ");
        query.setParameter("newsId", newsId).executeUpdate();
    }

    public void insertNewsRelations(int newsId, int[] deviceIds) {
        List<Device2News> toSave = getRelations(newsId, deviceIds, Device2News::createOf);

        device2NewsRepository.saveAll(toSave);
    }


    public void deleteAllAnnouncementRelations(int announcementId) {
        Query query = entityManager.createNativeQuery("delete from device2announcement tn where tn.announcement_id = :announcementId ");
        query.setParameter("announcementId", announcementId).executeUpdate();
    }

    public void insertAnnouncementRelations(int announcementId, int[] deviceIds) {
        List<Device2Announcement> toSave = getRelations(announcementId, deviceIds, Device2Announcement::createOf);

        device2AnnouncementRepository.saveAll(toSave);
    }

    public void deleteAllAdRelations(int adId) {
        Query query = entityManager.createNativeQuery("delete from device2ad tn where tn.ad_id = :adId ");
        query.setParameter("adId", adId).executeUpdate();
    }

    public void insertAdRelations(int adId, int[] deviceIds) {
        List<Device2Ad> toSave = getRelations(adId, deviceIds, Device2Ad::createOf);

        device2AdRepository.saveAll(toSave);
    }

    private <T> List<T> getRelations(int relationId, int[] deviceIds, BiFunction<
            Integer, Integer, T> createMethod) {

        return Arrays.stream(deviceIds).boxed()
                .map(deviceId -> createMethod.apply(deviceId, relationId))
                .collect(Collectors.toList());
    }

    @Transactional
    public void savePoint(DevicePointRequest request) {
        int deviceId = request.getDeviceId();
        int pointId = request.getPointId();

        Query removeOldDevicePoint = entityManager.createNativeQuery("update device set point_id=null where point_id=:pointId");
        removeOldDevicePoint.setParameter("pointId", pointId)
                .executeUpdate();

        Query addPointToDevice = entityManager.createNativeQuery("update device set point_id=:pointId where id=:deviceId");
        addPointToDevice.setParameter("pointId", pointId)
                .setParameter("deviceId", deviceId)
                .executeUpdate();
    }


    @Transactional
    public void removeDevicesFromPoint(int pointId) {
        Query removeOldDevicePoint = entityManager.createNativeQuery("update device set point_id=null where point_id=:pointId");
        removeOldDevicePoint.setParameter("pointId", pointId)
                .executeUpdate();
    }
}
