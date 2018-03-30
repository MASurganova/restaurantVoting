package ru.voting.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.voting.model.Restaurant;
import ru.voting.repository.RestaurantRepository;

import java.util.List;

@Repository
public class JpaRestaurantRepository implements RestaurantRepository {
    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return null;
    }

    @Override
    public Restaurant get(int id) {
        return null;
    }

    @Override
    public List<Restaurant> getAll() {
        return null;
    }

    @Override
    public Restaurant getByName(String name) {
        return null;
    }

    @Override
    public List<Restaurant> getEnabledRestaurants() {
        return null;
    }
}
