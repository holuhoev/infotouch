package ru.hse.infotouch.domain.service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.datasource.TerminalDatasource;
import ru.hse.infotouch.domain.dto.request.TerminalRequest;
import ru.hse.infotouch.domain.models.admin.Terminal;
import ru.hse.infotouch.domain.repo.TerminalRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class TerminalService {

    private final EntityManager entityManager;
    private final TerminalDatasource datasource;
    private final TerminalRepository terminalRepository;
    private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public TerminalService(EntityManager entityManager, TerminalDatasource datasource, TerminalRepository terminalRepository) {
        this.entityManager = entityManager;
        this.datasource = datasource;
        this.terminalRepository = terminalRepository;
    }

    public void insertNewsRelations(int newsId, int[] terminalIds) {

        // TODO:
//        Query query = entityManager.createNativeQuery("insert into terminal2news (news_id,terminal_id) values (:newsId, :terminalId) ")
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
}
