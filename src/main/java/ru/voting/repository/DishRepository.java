package ru.voting.repository;

import ru.voting.model.Dish;

public interface DishRepository {

    boolean delete(int id, int restaurantId);

    Dish save(Dish dish, int restaurantId) ;

    Dish get(int id, int restaurantId);

}
