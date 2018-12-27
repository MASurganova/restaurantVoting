package ru.voting.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.voting.AuthorizedUser;
import ru.voting.model.User;
import ru.voting.repository.UserRepository;
import ru.voting.to.UserTo;
import ru.voting.util.exception.NotFoundException;

import java.util.List;

import static ru.voting.util.UserUtil.updateFromTo;
import static ru.voting.util.ValidationUtil.checkNotFound;
import static ru.voting.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("users")
    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void update(User user) throws NotFoundException {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.get(user.getId()), user.getId());
        repository.update(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void update(UserTo userTo) throws NotFoundException {
        User user =  updateFromTo(get(userTo.getId()), userTo);
        update(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public User create(User  user) {
        return repository.save(user);
    }

    @Override
    public User getWithChoice(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.getWithChoice(id), id);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }

}
