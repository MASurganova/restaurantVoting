package ru.voting.web.user;

import ru.voting.model.User;
import ru.voting.to.UserTo;
import ru.voting.web.AbstractController;

import java.util.List;

import static ru.voting.util.ValidationUtil.assureIdConsistent;
import static ru.voting.util.ValidationUtil.checkNew;

public abstract class AbstractUserController extends AbstractController {

    public List<User> getAll() {
        log.info("getAll");
        return userService.getAllWithChoice();
    }

    public User get(int id) {
        log.info("get {}", id);
        return userService.get(id);
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return userService.create(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        userService.delete(id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        userService.update(user);
    }

    public void update(UserTo userTo, int id) {
        log.info("update {} with id={}", userTo, id);
        assureIdConsistent(userTo, id);
        userService.update(userTo);
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return userService.getByEmail(email);
    }
}