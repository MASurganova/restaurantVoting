package ru.voting.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.voting.model.Restaurant;
import ru.voting.repository.RestaurantRepository;

import java.util.List;

@Repository
public class DataJpaRestaurantRepositoryImpl implements RestaurantRepository {
    private static final Sort SORT_NAME = new Sort(Sort.Direction.ASC, "name");

    @Autowired
    CrudRestaurantRepository repository;

    @Override
    public boolean delete(int id) {
        return repository.removeById(id) != 0;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    @Override
    public Restaurant get(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Restaurant> getAll() {
        return repository.findAll(SORT_NAME);
    }

    @Override
    public Restaurant getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public Restaurant getWithLunch(int id) {
        return repository.getById(id).orElse(null);
    }

    @Override
    public List<Restaurant> getEnabledRestaurants() {
        return repository.findAllByEnabledIsTrue(SORT_NAME);
    }
}
