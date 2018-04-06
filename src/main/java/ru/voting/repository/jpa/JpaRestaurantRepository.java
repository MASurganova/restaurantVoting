package ru.voting.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Restaurant;
import ru.voting.repository.RestaurantRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaRestaurantRepository implements RestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Restaurant.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        if (restaurant.isNew()) {
            em.persist(restaurant);
            return restaurant;
        } else {
            return em.merge(restaurant);
        }
    }

    @Override
    public Restaurant get(int id) {
        return em.find(Restaurant.class, id);
    }

    @Override
    public List<Restaurant> getAll() {
        return em.createNamedQuery(Restaurant.ALL_WITH_LUNCH_SORTED, Restaurant.class).getResultList();
    }

    @Override
    public Restaurant getByName(String name) {
        List<Restaurant> restarants = em.createNamedQuery(Restaurant.BY_NAME, Restaurant.class)
                .setParameter(1, name)
                .getResultList();
        return DataAccessUtils.singleResult(restarants);
    }

    @Override
    public List<Restaurant> getEnabledRestaurants() {
        return em.createNamedQuery(Restaurant.ALL_ENABLED_SORTED, Restaurant.class).getResultList();
    }

    @Override
    public Restaurant getWithLunch(int id) {
        List<Restaurant> restarants = em.createNamedQuery(Restaurant.BY_ID_WITH_LUNCH, Restaurant.class)
                .setParameter(1, id)
                .getResultList();
        return DataAccessUtils.singleResult(restarants);
    }

    @Transactional
    @Override
    public void enabled(int id) {
        Restaurant restaurant = getWithLunch(id);
        restaurant.setEnabled(true);
        em.merge(restaurant);
    }

    @Transactional
    @Override
    public void addVoter(int id) {
        Restaurant restaurant = getWithLunch(id);
        restaurant.addVoter();
        em.merge(restaurant);
    }

    @Transactional
    @Override
    public void removeVoter(int id) {
        Restaurant restaurant = getWithLunch(id);
        restaurant.removeVoter();
        em.merge(restaurant);
    }

    @Transactional
    @Override
    public void disabled(int id) {
        Restaurant restaurant = getWithLunch(id);
        restaurant.setEnabled(false);
        em.merge(restaurant);
    }

    @Transactional
    @Override
    public void updateVoters(int id) {
        Restaurant restaurant = getWithLunch(id);
        restaurant.setVoters(0);
        em.merge(restaurant);
    }
}
