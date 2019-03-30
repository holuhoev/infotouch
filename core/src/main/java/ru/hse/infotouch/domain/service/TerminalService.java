package ru.hse.infotouch.domain.service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.datasource.TerminalDatasource;
import ru.hse.infotouch.domain.dto.request.TerminalRequest;
import ru.hse.infotouch.domain.models.admin.Terminal;
import ru.hse.infotouch.domain.models.admin.relations.*;
import ru.hse.infotouch.domain.repo.Terminal2AdRepository;
import ru.hse.infotouch.domain.repo.Terminal2AnnouncementRepository;
import ru.hse.infotouch.domain.repo.Terminal2NewsRepository;
import ru.hse.infotouch.domain.repo.TerminalRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
public class TerminalService {

    private final EntityManager entityManager;
    private final TerminalDatasource datasource;
    private final TerminalRepository terminalRepository;
    private final Terminal2NewsRepository terminal2NewsRepository;
    private final Terminal2AnnouncementRepository terminal2AnnouncementRepository;
    private final Terminal2AdRepository terminal2AdRepository;

    private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public TerminalService(EntityManager entityManager, TerminalDatasource datasource, TerminalRepository terminalRepository, Terminal2NewsRepository terminal2NewsRepository, Terminal2AnnouncementRepository terminal2AnnouncementRepository, Terminal2AdRepository terminal2AdRepository) {
        this.entityManager = entityManager;
        this.datasource = datasource;
        this.terminalRepository = terminalRepository;
        this.terminal2NewsRepository = terminal2NewsRepository;
        this.terminal2AnnouncementRepository = terminal2AnnouncementRepository;
        this.terminal2AdRepository = terminal2AdRepository;
    }


    public List<Terminal> findAll(String searchString, int page) {

        return datasource.findAll(searchString, page);
    }

    public Terminal getOneById(int id) {
        return this.terminalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Информационного устройства с id \"%d\" не существует", id)));
    }

    @Transactional
    public Terminal create(TerminalRequest request) {
        Terminal terminal = new Terminal();

        terminal.setLocation(geometryFactory.createPoint(new Coordinate(request.getX(), request.getY())));

        terminal.updateFromRequest(request);

        return terminalRepository.save(terminal);
    }

    @Transactional
    public Terminal update(int id, TerminalRequest request) {
        Terminal terminal = this.getOneById(id);

        terminal.setLocation(geometryFactory.createPoint(new Coordinate(request.getX(), request.getY())));
        terminal.updateFromRequest(request);

        return terminalRepository.save(terminal);
    }

    @Transactional
    public void delete(int id) {
        Terminal terminal = this.getOneById(id);

        terminalRepository.delete(terminal);
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
        Query query = entityManager.createNativeQuery("delete from terminal2news tn where tn.news_id = :newsId ");
        query.setParameter("newsId", newsId).executeUpdate();
    }

    public void insertNewsRelations(int newsId, int[] terminalIds) {
        List<Terminal2News> toSave = getRelations(newsId, terminalIds, Terminal2News::createOf);

        terminal2NewsRepository.saveAll(toSave);
    }


    public void deleteAllAnnouncementRelations(int announcementId) {
        Query query = entityManager.createNativeQuery("delete from terminal2announcement tn where tn.announcement_id = :announcementId ");
        query.setParameter("announcementId", announcementId).executeUpdate();
    }

    public void insertAnnouncementRelations(int announcementId, int[] terminalIds) {
        List<Terminal2Announcement> toSave = getRelations(announcementId, terminalIds, Terminal2Announcement::createOf);

        terminal2AnnouncementRepository.saveAll(toSave);
    }

    public void deleteAllAdRelations(int adId) {
        Query query = entityManager.createNativeQuery("delete from terminal2ad tn where tn.ad_id = :adId ");
        query.setParameter("adId", adId).executeUpdate();
    }

    public void insertAdRelations(int adId, int[] terminalIds) {
        List<Terminal2Ad> toSave = getRelations(adId, terminalIds, Terminal2Ad::createOf);

        terminal2AdRepository.saveAll(toSave);
    }

    private <T> List<T> getRelations(int relationId, int[] terminalIds, BiFunction<Integer, Integer, T> createMethod) {

        return Arrays.stream(terminalIds).boxed()
                .map(terminalId -> createMethod.apply(terminalId, relationId))
                .collect(Collectors.toList());
    }
}
