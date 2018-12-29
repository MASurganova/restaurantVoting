package ru.voting.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.voting.model.Dish;
import ru.voting.repository.DishRepository;

@Repository
public class DataJpaDishRepositoryImpl implements DishRepository {

    private static final Sort SORT_ID = new Sort(Sort.Direction.ASC, "id");

    @Autowired
    CrudDishRepository crudDishRepository;

    @Autowired
    CrudRestaurantRepository crudRestaurantRepository;

    @Override
    public boolean delete(int id, int restaurantId) {
        return crudDishRepository.removeByIdAndRestaurantId(id, restaurantId) != 0;
    }

    @Override
    public Dish save(Dish dish, int restaurantId) {
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null) {
            return null;
        }
        dish.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudDishRepository.save(dish);
    }

    @Override
    public Dish get(int id, int restaurantId) {
        return crudDishRepository.findByIdAndRestaurantId(id, restaurantId).orElse(null);
    }

}
