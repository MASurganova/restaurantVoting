package ru.voting.repository;

import ru.voting.model.Restaurant;
import ru.voting.model.User;

import java.util.List;

public interface UserRepository {

    boolean delete(int id);

    User get(int id);

    List<User> getAll();

    User getByEmail(String email);

    User save(User user);

    default void setChoice(User user, Restaurant restaurant) {
        user.setChoice(restaurant);
        save(user);
    }

}
