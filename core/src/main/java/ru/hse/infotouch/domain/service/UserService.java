package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.infotouch.domain.datasource.TerminalDatasource;
import ru.hse.infotouch.domain.datasource.UserDatasource;
import ru.hse.infotouch.domain.dto.request.UserRequest;
import ru.hse.infotouch.domain.dto.request.UserTerminalRequest;
import ru.hse.infotouch.domain.models.admin.User;
import ru.hse.infotouch.domain.models.admin.relations.User2Terminal;
import ru.hse.infotouch.domain.repo.User2TerminalRepository;
import ru.hse.infotouch.domain.repo.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserDatasource datasource;
    private final TerminalDatasource terminalDatasource;

    private final User2TerminalRepository user2TerminalRepository;

    private final EntityManager em;

    public UserService(UserRepository repository, UserDatasource datasource, TerminalDatasource terminalDatasource, User2TerminalRepository user2TerminalRepository, EntityManager em) {
        this.repository = repository;
        this.datasource = datasource;
        this.terminalDatasource = terminalDatasource;
        this.user2TerminalRepository = user2TerminalRepository;
        this.em = em;
    }

    public User getOneById(int id) {
        User user = this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Пользователя с id \"%d\" не существует", id)));
        user.setTerminals(terminalDatasource.findAllByUserId(id));

        return user;
    }

    public List<User> findAll(String searchString, int page) {

        return datasource.findAll(searchString, page);
    }

    @Transactional
    public User create(UserRequest userRequest) {
        User toSave = User.createFromRequest(userRequest);

        User user = repository.save(toSave);
        insertTerminalRelations(user.getId(), userRequest.getTerminals());

        user.setTerminals(terminalDatasource.findAllByUserId(user.getId()));

        return user;
    }

    @Transactional
    public User update(int id, UserRequest userRequest) {
        User user = this.getOneById(id).updateFromRequest(userRequest);

        deleteTerminalRelations(id);
        insertTerminalRelations(user.getId(), userRequest.getTerminals());

        User saved = this.repository.save(user);

        saved.setTerminals(terminalDatasource.findAllByUserId(id));

        return saved;
    }


    private void deleteTerminalRelations(int userId) {
        Query query = em.createNativeQuery("delete from user2terminal ut where ut.user_id = :userId ");
        query.setParameter("userId", userId).executeUpdate();
    }

    private void insertTerminalRelations(int userId, List<UserTerminalRequest> terminals) {
        List<User2Terminal> toSave = terminals.stream()
                .map(terminal -> User2Terminal.createOf(userId, terminal.getTerminalId(), terminal.getAccessRight()))
                .collect(Collectors.toList());

        user2TerminalRepository.saveAll(toSave);
    }
}
