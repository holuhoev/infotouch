package ru.hse.infotouch.domain.service;

import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.datasource.TerminalDatasource;
import ru.hse.infotouch.domain.models.admin.User;
import ru.hse.infotouch.domain.repo.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;
    private final TerminalDatasource terminalDatasource;

    public UserService(UserRepository repository, TerminalDatasource terminalDatasource) {
        this.repository = repository;
        this.terminalDatasource = terminalDatasource;
    }

    public User getOneById(int id) {
        User user = this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Пользователя с id \"%d\" не существует", id)));
        user.setTerminals(terminalDatasource.findAllByUserId(id));

        return user;
    }
}
