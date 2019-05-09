package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.datasource.AnnouncementDatasource;
import ru.hse.infotouch.domain.dto.request.AnnouncementRequest;
import ru.hse.infotouch.domain.models.admin.Announcement;
import ru.hse.infotouch.domain.repo.AnnouncementRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnnouncementService {
    private final AnnouncementDatasource announcementDatasource;
    private final AnnouncementRepository repository;
    private final DeviceService deviceService;

    public AnnouncementService(AnnouncementDatasource announcementDatasource, AnnouncementRepository repository, DeviceService deviceService) {
        this.announcementDatasource = announcementDatasource;
        this.repository = repository;
        this.deviceService = deviceService;
    }

    public List<Announcement> findAll(Integer deviceId,
                                      String searchString,
                                      LocalDate from,
                                      LocalDate to,
                                      int page) {

        return announcementDatasource.findAll(deviceId, searchString, from, to, page);
    }

    public Announcement getOneById(int id) {

        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Объявление с id \"%d\" не существует", id)));
    }

    @Transactional
    public Announcement create(AnnouncementRequest request) {
        requireExistingRelations(request);

        // TODO: set createdBy when Security;

        Announcement announcement = repository.save(Announcement.createFromRequest(request));

        deviceService.insertAnnouncementRelations(announcement.getId(), request.getDeviceIds());


        return announcement;
    }

    @Transactional
    public Announcement update(int id, AnnouncementRequest request) {
        requireExistingRelations(request);

        Announcement announcement = this.getOneById(id)
                .updateFromRequest(request);

        deviceService.deleteAllAnnouncementRelations(id);
        deviceService.insertAnnouncementRelations(id, request.getDeviceIds());

        return repository.save(announcement);
    }

    @Transactional
    public void delete(int id) {
        final Announcement announcement = this.getOneById(id);

        deviceService.deleteAllAnnouncementRelations(id);

        this.repository.delete(announcement);
    }

    private void requireExistingRelations(AnnouncementRequest announcementRequest) {

        if (deviceService.isNotExistAll(announcementRequest.getDeviceIds())) {
            throw new IllegalArgumentException("Does not all devices exist.");
        }

    }


}
