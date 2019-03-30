package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.datasource.UserDatasource;
import ru.hse.infotouch.domain.dto.request.UserTerminalRequest;
import ru.hse.infotouch.domain.models.admin.Terminal;
import ru.hse.infotouch.domain.models.admin.User;
import ru.hse.infotouch.domain.models.admin.relations.User2Terminal;
import ru.hse.infotouch.domain.repo.User2TerminalRepository;
import ru.hse.infotouch.domain.repo.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final TerminalService terminalService;
    private final UserRepository repository;
    private final User2TerminalRepository user2TerminalRepository;
    private final UserDatasource userDatasource;

    public UserService(TerminalService terminalService, UserRepository repository, User2TerminalRepository user2TerminalRepository, UserDatasource userDatasource) {
        this.terminalService = terminalService;
        this.repository = repository;
        this.user2TerminalRepository = user2TerminalRepository;
        this.userDatasource = userDatasource;
    }

    public User getOneById(int id) {
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Пользователя с id \"%d\" не существует", id)));
    }

    public User2Terminal addTerminal(int id, UserTerminalRequest request) {
        Objects.requireNonNull(request.getAccessRight());

        User user = this.getOneById(id);
        Terminal terminal = terminalService.getOneById(request.getTerminalId());

        User2Terminal user2Terminal = new User2Terminal(user.getId(), terminal.getId(), request.getAccessRight());

        return user2TerminalRepository.save(user2Terminal);
    }

    public List<User2Terminal> findAllWithTerminals(String searchString, int page) {

        return userDatasource.findAll(searchString, page);
    }
}
