package ru.voting.repository.mock;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.voting.TestData;
import ru.voting.model.Restaurant;
import ru.voting.repository.RestaurantRepository;

import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryRestaurantRepository extends AbstractInMemoryRepository<Restaurant> implements RestaurantRepository {
    private static final Logger log =getLogger(InMemoryUserRepository.class);

    public InMemoryRestaurantRepository() {
        super();
        inMemoryCount = new AtomicInteger(2);
    }

    public void init() {
        inMemoryRepository.clear();
        TestData.MY.addDish(TestData.DISH_1);
        TestData.MY.addDish(TestData.DISH_2);
        TestData.OTHER.addDish(TestData.DISH_3);
        TestData.OTHER.addDish(TestData.DISH_4);
        TestData.OTHER.addDish(TestData.DISH_5);
        inMemoryRepository.put(TestData.MY.getId(), TestData.MY);
        inMemoryRepository.put(TestData.OTHER.getId(), TestData.OTHER);
    }

    public Restaurant getByName(String name) {
        log.info("get by name {}", name);
        return inMemoryRepository.values().stream().filter(r -> r.getName().equals(name))
                .findFirst().orElse(null);
    }

    @Override
    public void addVoter(Restaurant restaurant) {
        log.info("add voter by {}", restaurant);
        restaurant.addVoter();
        save(restaurant);
    }

    @Override
    public void removeVoter(Restaurant restaurant) {
        log.info("remove voter by {}", restaurant);
        restaurant.removeVoter();
        save(restaurant);
    }

    @Override
    public void updateVoters(Restaurant restaurant) {
        log.info("update voter by {}", restaurant);
        restaurant.setVoters(0);
        save(restaurant);
    }

    @Override
    public void enabled(Restaurant restaurant) {
        log.info("enabled {}", restaurant);
        restaurant.setEnabled(true);
        save(restaurant);
    }

    @Override
    public void disabled(Restaurant restaurant) {
        log.info("disabled {}", restaurant);
        restaurant.setEnabled(false);
        save(restaurant);
    }
}
