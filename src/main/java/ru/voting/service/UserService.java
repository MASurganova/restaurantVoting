package ru.voting.service;

import ru.voting.model.User;
import ru.voting.util.exception.NotFoundException;

import java.util.List;

import static ru.voting.util.ValidationUtil.checkNotFound;
import static ru.voting.util.ValidationUtil.checkNotFoundWithId;

public class UserService {

    public User create(User user) {
        return null;
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(null, id);
    }

    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(null, id);
    }

    public User getByEmail(String email) throws NotFoundException {
        return checkNotFound(null, "email=" + email);
    }

    public List<User> getAll() {
        return null;
    }

    public void update(User user) {}

}
