package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.datasource.AdDatasource;
import ru.hse.infotouch.domain.dto.request.AdRequest;
import ru.hse.infotouch.domain.models.admin.Ad;

import ru.hse.infotouch.domain.repo.AdRepository;

import java.util.List;

@Service
public class AdService {
    private final AdDatasource datasource;
    private final AdRepository repository;
    private final TerminalService terminalService;

    public AdService(AdDatasource datasource, AdRepository repository, TerminalService terminalService) {
        this.datasource = datasource;
        this.repository = repository;
        this.terminalService = terminalService;
    }

    public Ad getOneById(int id) {

        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Рекламы с id \"%d\" не существует", id)));
    }

    public List<Ad> findAll(String searchString, int page) {
        return datasource.findAll(searchString, page);
    }

    @Transactional
    public Ad create(AdRequest adRequest) {
        requireExistingRelations(adRequest);

        Ad ad = Ad.createFromRequest(adRequest);

        return repository.save(ad);
    }

    @Transactional
    public Ad update(int id, AdRequest adRequest) {
        requireExistingRelations(adRequest);

        Ad ad = this.getOneById(id)
                .updateFromRequest(adRequest);

        return repository.save(ad);
    }

    public void delete(int id) {
        final Ad ad = this.getOneById(id);

        repository.delete(ad);
    }

    public void requireExistingRelations(AdRequest request) {
        if (request.getTerminalIds() != null && terminalService.isNotExistAll(request.getTerminalIds())) {
            throw new IllegalArgumentException("Does not all terminals exist.");
        }
    }
}
