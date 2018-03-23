package ru.voting.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voting.model.User;
import ru.voting.repository.UserRepository;
import ru.voting.util.exception.NotFoundException;
import ru.voting.util.ValidationUtil;

import java.util.List;

import static ru.voting.util.ValidationUtil.checkNotFound;
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

    public void update(User user) throws NotFoundException {
        checkNotFoundWithId(repository.get(user.getId()), user.getId());
        repository.save(user);
    }

    public void create(User  user) {
        repository.save(user);
    }

}