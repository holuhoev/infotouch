package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.datasource.AnnouncementDatasource;
import ru.hse.infotouch.domain.dto.request.AnnouncementRequest;
import ru.hse.infotouch.domain.models.admin.Announcement;
import ru.hse.infotouch.domain.models.admin.HseLocation;
import ru.hse.infotouch.domain.repo.AnnouncementRepository;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class AnnouncementService {
    private final AnnouncementDatasource announcementDatasource;
    private final AnnouncementRepository repository;
    private final TerminalService terminalService;
    private final HseLocationService hseLocationService;

    public AnnouncementService(AnnouncementDatasource announcementDatasource, AnnouncementRepository repository, TerminalService terminalService, HseLocationService hseLocationService) {
        this.announcementDatasource = announcementDatasource;
        this.repository = repository;
        this.terminalService = terminalService;
        this.hseLocationService = hseLocationService;
    }

    public List<Announcement> findAll(String searchString,
                                      Integer hseLocationId,
                                      int page) {

        return announcementDatasource.findAll(searchString, hseLocationId, page);
    }

    public Announcement getOneById(int id) {
        Announcement announcement = this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Объявление с id \"%d\" не существует", id)));

        announcement.setHseLocation(hseLocationService.getOneById(announcement.getHseLocationId()));

        return announcement;
    }

    @Transactional
    public Announcement create(AnnouncementRequest request) {
        requireExistingRelations(request);

        // TODO: set createdBy when Security;

        Announcement announcement = repository.save(Announcement.createFromRequest(request));

        terminalService.insertAnnouncementRelations(announcement.getId(), request.getTerminalIds());

        HseLocation hseLocation = hseLocationService.getOneById(announcement.getId());

        announcement.setHseLocation(hseLocation);

        return announcement;
    }

    @Transactional
    public Announcement update(int id, AnnouncementRequest request) {
        requireExistingRelations(request);

        Announcement announcement = this.getOneById(id)
                .updateFromRequest(request);

        terminalService.deleteAllAnnouncementRelations(id);
        terminalService.insertAnnouncementRelations(id, request.getTerminalIds());
        Announcement saved = repository.save(announcement);

        saved.setHseLocation(hseLocationService.getOneById(request.getHseLocationId()));

        return saved;
    }

    @Transactional
    public void delete(int id) {
        final Announcement announcement = this.getOneById(id);

        terminalService.deleteAllAnnouncementRelations(id);

        this.repository.delete(announcement);
    }

    private void requireExistingRelations(AnnouncementRequest announcementRequest) {
        // TODO: uiException
        if (nonNull(announcementRequest.getHseLocationId()) && hseLocationService.isNotExist(announcementRequest.getHseLocationId())) {
            throw new IllegalArgumentException("Hse location does not exist.");
        }

        if (terminalService.isNotExistAll(announcementRequest.getTerminalIds())) {
            throw new IllegalArgumentException("Does not all terminals exist.");
        }

    }


}
