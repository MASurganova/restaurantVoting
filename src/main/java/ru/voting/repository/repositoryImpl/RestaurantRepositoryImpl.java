package ru.voting.repository.repositoryImpl;

import org.springframework.stereotype.Repository;
import ru.voting.model.Restaurant;
import ru.voting.repository.RestaurantRepository;

import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    public RestaurantRepositoryImpl() {
    }

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
    public void addVoter(Restaurant restaurant) {

    }

    @Override
    public void removeVoter(Restaurant restaurant) {

    }

    @Override
    public void updateVoters(Restaurant restaurant) {

    }

    @Override
    public void enabled(Restaurant restaurant) {

    }

    @Override
    public void disabled(Restaurant restaurant) {

    }
}
