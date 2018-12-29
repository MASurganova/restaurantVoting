package ru.voting.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Dish;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {

    @Transactional
    int removeByIdAndRestaurantId(int id, int restaurantId);

    @Override
    @Transactional
    Dish save(Dish user);

    Optional<Dish> findByIdAndRestaurantId(int id, int restaurantId);

    @Override
    List<Dish> findAll(Sort sort);

}
