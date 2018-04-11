package ru.voting.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.voting.model.Dish;
import ru.voting.repository.DishRepository;

import java.util.List;

@Repository
public class DataJpaDishRepositoryImpl implements DishRepository {

    private static final Sort SORT_ID = new Sort(Sort.Direction.ASC, "id");

    @Autowired
    CrudDishRepository repository;

    @Override
    public boolean delete(int id) {
        return repository.removeById(id) != 0;
    }

    @Override
    public Dish save(Dish dish) {
        return repository.save(dish);
    }

    @Override
    public Dish get(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Dish> getAll() {
        return repository.findAll(SORT_ID);
    }
}
