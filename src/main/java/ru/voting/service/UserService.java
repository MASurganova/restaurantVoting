package ru.voting.service;

import ru.voting.model.User;
import ru.voting.repository.UserRepository;
import ru.voting.util.exception.NotFoundException;

import java.util.List;

import static ru.voting.util.ValidationUtil.checkNotFound;
import static ru.voting.util.ValidationUtil.checkNotFoundWithId;

public class UserService {

    UserRepository repository;

    public User create(User user) {
        return repository.save(user);
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) throws NotFoundException {
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public void update(User user) { repository.save(user); }

}
