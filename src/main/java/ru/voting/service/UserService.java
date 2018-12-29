package ru.voting.service;


import ru.voting.model.User;
import ru.voting.to.UserTo;
import ru.voting.util.exception.NotFoundException;

import java.util.List;


public interface UserService {

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException ;

    List<User> getAll();

    List<User> getAllWithChoice();

    User getByEmail(String email) throws NotFoundException;

    void update(User user) throws NotFoundException;

    void update(UserTo userTo) throws NotFoundException;

    User create(User  user);

    User getWithChoice(int id);
}
