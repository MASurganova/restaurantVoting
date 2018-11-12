package ru.voting.repository;

import ru.voting.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    boolean delete(int id);

    Restaurant save(Restaurant restaurant) ;

    Restaurant get(int id);

    List<Restaurant> getAll();

    Restaurant getByName(String name);

    default void addVoter(int id) {
        Restaurant restaurant = getWithLunch(id);
        restaurant.addVoter();
        save(restaurant);
    }

    default void removeVoter(int id) {
        Restaurant restaurant = getWithLunch(id);
        restaurant.removeVoter();
        save(restaurant);
    }

    default void updateVoters(int id) {
        Restaurant restaurant = getWithLunch(id);
        restaurant.setVoters(0);
        save(restaurant);
    }

    default void enabled(int id) {
        Restaurant restaurant = getWithLunch(id);
        if(restaurant.isEnabled()) restaurant.setEnabled(false);
        else restaurant.setEnabled(true);
        save(restaurant);
    }

    default void disabled(int id) {
        Restaurant restaurant = getWithLunch(id);
        restaurant.setEnabled(false);
        save(restaurant);
    }

    default Restaurant getWithLunch(int id) {
        throw new UnsupportedOperationException();
    }

    List<Restaurant> getEnabledRestaurants();

}
