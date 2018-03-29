package ru.voting.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.voting.model.User;
import ru.voting.repository.UserRepository;
import ru.voting.util.ValidationUtil;
import ru.voting.util.exception.NotFoundException;

import java.util.List;

import static ru.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void delete(int id) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public User getByEmail(String email) throws NotFoundException {
        return ValidationUtil.checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    public User update(User user) throws NotFoundException {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.get(user.getId()), user.getId());
        return repository.save(user);
    }

    public User create(User  user) {
        return repository.save(user);
    }

}
