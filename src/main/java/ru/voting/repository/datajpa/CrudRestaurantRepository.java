package ru.voting.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT r FROM Restaurant r")
    Optional<List<Restaurant>> getAll(Sort sort);

    @EntityGraph(attributePaths = "lunch")
    List<Restaurant> findAll(Sort sort);

    @EntityGraph(attributePaths = "lunch")
    Restaurant getByName(String name);

    Optional<Restaurant> findById(int id);

    @EntityGraph(attributePaths = "lunch")
    Optional<Restaurant> getById(int id);

    @EntityGraph(attributePaths = "lunch")
    List<Restaurant> findAllByEnabledIsTrue(Sort sort);

    @Modifying
    @Transactional
    @Query("UPDATE Restaurant r SET r.voters=:voters WHERE r.id=:id")
    void updateVoters(@Param("id")Integer id,  @Param("voters") Integer voters);

    @Modifying
    @Transactional
    @Query("UPDATE Restaurant r SET r.name=:name WHERE r.id=:id")
    void update(@Param("id")Integer id,  @Param("name") String name);

    @Modifying
    @Transactional
    @Query("UPDATE Restaurant r SET r.enabled=:enabled WHERE r.id=:id")
    void updateEnabled(@Param("id")Integer id,  @Param("enabled") Boolean enabled);

}
