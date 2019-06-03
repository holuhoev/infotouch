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


    public AnnouncementService(AnnouncementDatasource announcementDatasource, AnnouncementRepository repository) {
        this.announcementDatasource = announcementDatasource;
        this.repository = repository;
    }

    public List<Announcement> findAll(Integer deviceId,
                                      String searchString,
                                      int page,
                                      int pageSize) {

        return announcementDatasource.findAll(deviceId, searchString, page,pageSize);
    }

    public Announcement getOneById(int id) {

        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Объявление с id \"%d\" не существует", id)));
    }

    @Transactional
    public Announcement create(AnnouncementRequest request) {
        // TODO: set createdBy when Security;

        return repository.save(Announcement.createFromRequest(request));
    }

    @Transactional
    public Announcement update(int id, AnnouncementRequest request) {
        Announcement announcement = this.getOneById(id)
                .updateFromRequest(request);

        return repository.save(announcement);
    }

    @Transactional
    public void delete(int id) {
        final Announcement announcement = this.getOneById(id);

        this.repository.delete(announcement);
    }
}
