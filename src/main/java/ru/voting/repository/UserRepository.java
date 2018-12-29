package ru.voting.repository;

import ru.voting.model.User;

import java.util.List;

public interface UserRepository {

    boolean delete(int id);

    User get(int id);

    List<User> getAll();

    User getByEmail(String email);

    User save(User user);

    User save(User user, Integer restaurantId);

    default void update(User user) { save(user); }

    default List<User> getAllWithChoice() { return getAll(); }

    default User getWithChoice(int id) {
        throw new UnsupportedOperationException();
    }
}
