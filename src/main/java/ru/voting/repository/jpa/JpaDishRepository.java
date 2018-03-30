package ru.voting.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.voting.model.Dish;
import ru.voting.repository.DishRepository;

import java.util.List;

@Repository
public class JpaDishRepository implements DishRepository {
    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Dish save(Dish dish) {
        return null;
    }

    @Override
    public Dish get(int id) {
        return null;
    }

    @Override
    public List<Dish> getAll() {
        return null;
    }
}
