package ru.voting.repository;

import ru.voting.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    boolean delete(int id);

    Restaurant save(Restaurant restaurant) ;

    Restaurant get(int id);

    List<Restaurant> getAll();

    Restaurant getByName(String name);

    default void addVoter(Restaurant restaurant) {
        restaurant.addVoter();
        save(restaurant);
    }

    default void removeVoter(Restaurant restaurant) {
        restaurant.removeVoter();
        save(restaurant);
    }

    default void updateVoters(Restaurant restaurant) {
        restaurant.setVoters(0);
        save(restaurant);
    }

    default void enabled(Restaurant restaurant) {
        restaurant.setEnabled(true);
        save(restaurant);
    }

    default void disabled(Restaurant restaurant) {
        restaurant.setEnabled(false);
        save(restaurant);
    }

    default Restaurant getWithLunch(int id) {
        throw new UnsupportedOperationException();
    }

    List<Restaurant> getEnabledRestaurants();

}
