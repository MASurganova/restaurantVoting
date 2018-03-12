package ru.voting.repository;

import org.slf4j.Logger;
import ru.voting.model.User;

import java.util.Collections;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class UserRepository {

    private static final Logger log =getLogger(UserRepository.class);

    public boolean delete(int id) {
        log.info("delete {}", id);
        return true;
    }

    public User save(User user) {
        log.info("save {}", user);
        return user;
    }

   public User get(int id) {
        log.info("get {}", id);
        return null;
    }

    public List<User> getAll() {
        log.info("getAll");
        return Collections.emptyList();
    }

    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return null;
    }
}
