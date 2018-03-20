package ru.voting.repository;

import ru.voting.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    boolean delete(int id);

    Restaurant save(Restaurant restaurant) ;

    Restaurant get(int id);

    List<Restaurant> getAll();

    Restaurant getByName(String name);

    void addVoter(Restaurant restaurant);

    void removeVoter(Restaurant restaurant);

    void updateVoters(Restaurant restaurant);

    void enabled(Restaurant restaurant);

    void disabled(Restaurant restaurant);
}
