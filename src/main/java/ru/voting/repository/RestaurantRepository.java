package ru.voting.repository;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.voting.service.VotingService;
import ru.voting.util.RestaurantUtil;
import ru.voting.model.Restaurant;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.voting.util.RestaurantUtil.*;

@Repository
public class RestaurantRepository extends AbstractInMemoryRepository<Restaurant> {
    private static final Logger log =getLogger(UserRepository.class);

    public RestaurantRepository() {
        super();
        save(MY);
        save(OTHER);
    }

    //    public boolean delete(int id) {
//        log.info("delete {}", id);
//        return true;
//    }
//
//    public Restaurant save(Restaurant restaurant) {
//        log.info("save {}", restaurant);
//        return restaurant;
//    }
//
//    public Restaurant get(int id) {
//        log.info("get {}", id);
//        return null;
//    }
//
//    public List<Restaurant> getAll() {
//        log.info("getAll");
//        return Collections.emptyList();
//    }

    public Restaurant getByName(String name) {
        log.info("get by name {}", name);
        return inMemoryRepository.values().stream().filter(r -> r.getName().equals(name))
                .findFirst().orElse(null);
    }

    public void addVoter(Restaurant restaurant) {
        restaurant.addVoter();
        save(restaurant);
    }

    public void removeVoter(Restaurant restaurant) {
        restaurant.removeVoter();
        save(restaurant);
    }

    public void updateVoters(Restaurant restaurant) {
        restaurant.setVoters(0);
        save(restaurant);
    }

    public void enabled(Restaurant restaurant) {
        restaurant.setEnabled(true);
        save(restaurant);
    }

    public void disabled(Restaurant restaurant) {
        restaurant.setEnabled(false);
        save(restaurant);
    }
}
