package ru.voting.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    int removeById(int id);

    @Override
    @Transactional
    Restaurant save(Restaurant restaurant);

    @Override
    Optional<Restaurant> findById(Integer id);

    @EntityGraph(attributePaths = "lunch")
    List<Restaurant> findAll(Sort sort);

    Restaurant getByName(String name);

//    Restaurant findById(int id);

    @EntityGraph(attributePaths = "lunch")
    Optional<Restaurant> getById(int id);

    @EntityGraph(attributePaths = "lunch")
    List<Restaurant> findAllByEnabledIsTrue(Sort sort);
}
