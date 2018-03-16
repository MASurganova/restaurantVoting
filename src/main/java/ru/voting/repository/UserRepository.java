package ru.voting.repository;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.voting.model.Restaurant;
import ru.voting.model.Role;
import ru.voting.model.User;

import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class UserRepository extends AbstractInMemoryRepository<User>{

    private static final Logger log = getLogger(UserRepository.class);

    public UserRepository() {
        super();
        save (new User(null, "USER", "user@mail.ru",
                "password", Role.ROLE_USER));
        save(new User(null, "ADMIN", "admin@mail.ru",
                "password", Role.ROLE_ADMIN, Role.ROLE_USER));
    }

    public void setChoice(User user, Restaurant restaurant) {
        user.setChoice(restaurant);
        save(user);
    }

//    public boolean delete(int id) {
//        log.info("delete {}", id);
//        return true;
//    }
//
//    public User save(User user) {
//        log.info(user.isNew() ? "create {}" : "update {}", user);
//        return user;
//    }
//
//   public User get(int id) {
//        log.info("get {}", id);
//        return null;
//    }
//
//    public List<User> getAll() {
//        log.info("getAll");
//        return Collections.EMPTY_LIST;
//    }

    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return inMemoryRepository.values().stream().filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }
}
