package ru.voting.util;

import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.model.Role;
import ru.voting.model.User;
import ru.voting.to.DishTo;
import ru.voting.to.UserTo;

public class DishUtil {

    public static Dish createNewFromTo(DishTo newDish) {
        return new Dish(null, newDish.getDescription(), newDish.getPrice());
    }

    public static Dish updateFromTo(Dish dish, DishTo dishTo) {
        dish.setDescription(dishTo.getDescription());
        dish.setPrice(dishTo.getPrice());
        return dish;
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}