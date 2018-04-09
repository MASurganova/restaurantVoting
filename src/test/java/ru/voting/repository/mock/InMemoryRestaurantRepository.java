package ru.voting.repository.mock;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.voting.model.Restaurant;
import ru.voting.repository.RestaurantRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.voting.TestData.*;

@Repository
public class InMemoryRestaurantRepository extends AbstractInMemoryRepository<Restaurant> implements RestaurantRepository {
    public static final Logger log = getLogger(InMemoryRestaurantRepository.class);

    public InMemoryRestaurantRepository() {
        super(log);
        init();
    }

    public void init() {
        inMemoryRepository.clear();
        Restaurant my = new Restaurant(MY);
        Restaurant other = new Restaurant(OTHER);
        my.addDish(DISH_1);
        my.addDish(DISH_2);
        other.addDish(DISH_3);
        other.addDish(DISH_4);
        other.addDish(DISH_5);
        my.setEnabled(true);
        inMemoryRepository.put(my.getId(), my);
        inMemoryRepository.put(other.getId(), other);
        inMemoryCount = new AtomicInteger(100001);
    }

    public Restaurant getByName(String name) {
        log.info("get by name {}", name);
        return inMemoryRepository.values().stream().filter(r -> r.getName().equals(name))
                .findFirst().orElse(null);
    }

    @Override
    public List<Restaurant> getEnabledRestaurants() {
        return getAll().stream().filter(Restaurant::isEnabled).collect(Collectors.toList());
    }
}
