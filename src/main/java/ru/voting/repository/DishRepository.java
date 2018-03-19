package ru.voting.repository;

import ru.voting.model.Dish;
import ru.voting.model.User;

import java.util.List;

public interface DishRepository {

    boolean delete(int id);

    Dish save(Dish dish) ;

    Dish get(int id);

    List<Dish> getAll();
}
