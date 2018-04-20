package ru.voting.repository;

import ru.voting.model.Dish;

public interface DishRepository {

    boolean delete(int id);

    Dish save(Dish dish) ;

    Dish get(int id);

}
