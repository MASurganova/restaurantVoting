package ru.voting.repository.MockRepository;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.voting.model.Restaurant;
import ru.voting.repository.MockRepository.AbstractInMemoryRepository;
import ru.voting.repository.MockRepository.InMemoryUserRepository;
import ru.voting.repository.RestaurantRepository;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.voting.util.RestaurantUtil.*;

@Repository
public class InMemoryRestaurantRepository extends AbstractInMemoryRepository<Restaurant> implements RestaurantRepository {
    private static final Logger log =getLogger(InMemoryUserRepository.class);

    public InMemoryRestaurantRepository() {
        super();
        save(MY);
        save(OTHER);
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
