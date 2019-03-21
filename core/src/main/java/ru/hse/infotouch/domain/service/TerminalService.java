package ru.hse.infotouch.domain.service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.datasource.TerminalDatasource;
import ru.hse.infotouch.domain.dto.request.TerminalRequest;
import ru.hse.infotouch.domain.models.admin.Terminal;
import ru.hse.infotouch.domain.models.admin.relations.Terminal2News;
import ru.hse.infotouch.domain.models.admin.relations.Terminal2NewsId;
import ru.hse.infotouch.domain.repo.Terminal2NewsRepository;
import ru.hse.infotouch.domain.repo.TerminalRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TerminalService {

    private final EntityManager entityManager;
    private final TerminalDatasource datasource;
    private final TerminalRepository terminalRepository;
    private final Terminal2NewsRepository terminal2NewsRepository;

    private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public TerminalService(EntityManager entityManager, TerminalDatasource datasource, TerminalRepository terminalRepository, Terminal2NewsRepository terminal2NewsRepository) {
        this.entityManager = entityManager;
        this.datasource = datasource;
        this.terminalRepository = terminalRepository;
        this.terminal2NewsRepository = terminal2NewsRepository;
    }

    public void deleteAllNewsRelations(int newsId) {
        Query query = entityManager.createNativeQuery("delete from terminal2news tn where tn.news_id = :newsId ");
        query.setParameter("newsId", newsId).executeUpdate();
    }

    public void insertNewsRelations(int newsId, int[] terminalIds) {
        List<Terminal2News> toSave = Arrays.stream(terminalIds).boxed().map(id -> {
            Terminal2NewsId terminal2NewsId = new Terminal2NewsId(id, newsId);

            return new Terminal2News(terminal2NewsId);
        }).collect(Collectors.toList());

        terminal2NewsRepository.saveAll(toSave);
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

    public boolean isNotExistAll(int[] ids) {
        for (int id : ids) {
            if (!terminalRepository.existsById(id)) {
                return true;
            }
        }

        return false;
    }

}
