package ru.voting.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("users")
    public List<User> getAll() {
        return repository.getAll();
    }

    public User getByEmail(String email) throws NotFoundException {
        return ValidationUtil.checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @CacheEvict(value = "users", allEntries = true)
    public User update(User user) throws NotFoundException {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.get(user.getId()), user.getId());
        User updated = repository.get(user.getId());
        updated.setName(user.getName());
        updated.setPassword(user.getPassword());
        updated.setEmail(user.getEmail());
        return repository.save(updated);
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User  user) {
        return repository.save(user);
    }

    public User getWithChoice(int id) { return repository.getWithChoice(id); }

}
