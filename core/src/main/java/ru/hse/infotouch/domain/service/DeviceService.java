package ru.hse.infotouch.domain.service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.datasource.DeviceDatasource;
import ru.hse.infotouch.domain.dto.request.DeviceRequest;
import ru.hse.infotouch.domain.models.admin.Device;
import ru.hse.infotouch.domain.models.admin.relations.*;
import ru.hse.infotouch.domain.repo.Device2AdRepository;
import ru.hse.infotouch.domain.repo.Device2AnnouncementRepository;
import ru.hse.infotouch.domain.repo.Device2NewsRepository;
import ru.hse.infotouch.domain.repo.DeviceRepository;

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
    private final DeviceRepository terminalRepository;
    private final Device2NewsRepository terminal2NewsRepository;
    private final Device2AnnouncementRepository terminal2AnnouncementRepository;
    private final Device2AdRepository terminal2AdRepository;

    private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public DeviceService(EntityManager entityManager, DeviceDatasource datasource, DeviceRepository terminalRepository, Device2NewsRepository terminal2NewsRepository, Device2AnnouncementRepository terminal2AnnouncementRepository, Device2AdRepository terminal2AdRepository) {
        this.entityManager = entityManager;
        this.datasource = datasource;
        this.terminalRepository = terminalRepository;
        this.terminal2NewsRepository = terminal2NewsRepository;
        this.terminal2AnnouncementRepository = terminal2AnnouncementRepository;
        this.terminal2AdRepository = terminal2AdRepository;
    }


    public List<Device> findAll(String searchString, int page) {

        return datasource.findAll(searchString, page);
    }

    public Device getOneById(int id) {
        return this.terminalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Информационного устройства с id \"%d\" не существует", id)));
    }

    @Transactional
    public Device create(DeviceRequest request) {
        Device device = new Device();

        device.setLocation(geometryFactory.createPoint(new Coordinate(request.getX(), request.getY())));

        device.updateFromRequest(request);

        return terminalRepository.save(device);
    }

    @Transactional
    public Device update(int id, DeviceRequest request) {
        Device device = this.getOneById(id);

        device.setLocation(geometryFactory.createPoint(new Coordinate(request.getX(), request.getY())));
        device.updateFromRequest(request);

        return terminalRepository.save(device);
    }

    @Transactional
    public void delete(int id) {
        Device device = this.getOneById(id);

        terminalRepository.delete(device);
    }

    public boolean isNotExistAll(int... ids) {
        for (int id : ids) {
            if (!terminalRepository.existsById(id)) {
                return true;
            }
        }

        return false;
    }

    public void deleteAllNewsRelations(int newsId) {
        Query query = entityManager.createNativeQuery("delete from device2news tn where tn.news_id = :newsId ");
        query.setParameter("newsId", newsId).executeUpdate();
    }

    public void insertNewsRelations(int newsId, int[] terminalIds) {
        List<Device2News> toSave = getRelations(newsId, terminalIds, Device2News::createOf);

        terminal2NewsRepository.saveAll(toSave);
    }


    public void deleteAllAnnouncementRelations(int announcementId) {
        Query query = entityManager.createNativeQuery("delete from device2announcement tn where tn.announcement_id = :announcementId ");
        query.setParameter("announcementId", announcementId).executeUpdate();
    }

    public void insertAnnouncementRelations(int announcementId, int[] terminalIds) {
        List<Device2Announcement> toSave = getRelations(announcementId, terminalIds, Device2Announcement::createOf);

        terminal2AnnouncementRepository.saveAll(toSave);
    }

    public void deleteAllAdRelations(int adId) {
        Query query = entityManager.createNativeQuery("delete from device2ad tn where tn.ad_id = :adId ");
        query.setParameter("adId", adId).executeUpdate();
    }

    public void insertAdRelations(int adId, int[] terminalIds) {
        List<Device2Ad> toSave = getRelations(adId, terminalIds, Device2Ad::createOf);

        terminal2AdRepository.saveAll(toSave);
    }

    private <T> List<T> getRelations(int relationId, int[] terminalIds, BiFunction<Integer, Integer, T> createMethod) {

        return Arrays.stream(terminalIds).boxed()
                .map(terminalId -> createMethod.apply(terminalId, relationId))
                .collect(Collectors.toList());
    }
}
