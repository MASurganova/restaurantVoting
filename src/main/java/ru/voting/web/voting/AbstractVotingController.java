package ru.voting.web.voting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.model.User;
import ru.voting.service.UserService;
import ru.voting.service.VotingService;
import ru.voting.util.exception.NotFoundException;
import ru.voting.util.exception.TimeDelayException;

import java.time.LocalTime;
import java.util.List;

import static ru.voting.util.ValidationUtil.assureIdConsistent;
import static ru.voting.util.ValidationUtil.checkNew;
import static ru.voting.util.ValidationUtil.checkNotFound;

public abstract class AbstractVotingController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VotingService service;

    @Autowired
    private UserService userService;

    public List<Restaurant> getAllRestaurants() {
        log.info("getAllRestaurants");
        return service.getAllRestaurants();
    }

    public List<Restaurant> getEnabledRestaurants() {
        log.info("getEnabledRestaurants");
        return service.getCurrentRestaurants();
    }

    public Restaurant getChoice() {
        log.info("getChoice");
        return service.getCurrentChoice();
    }

    public List<Restaurant> getVoting() {
        log.info("getVoting");
        return service.getCurrentRestaurants();
    }

    public Restaurant get(int id) {
        log.info("get {}", id);
        return service.getRestaurantById(id);
    }

    public Restaurant getWithLunch(int id) {
        log.info("getWithLunch {}", id);
        return service.getRestaurantByIdWithLunch(id);
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return service.createRestaurant(restaurant);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.deleteRestaurant(id);
    }

    public void update(Restaurant restaurant, int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        service.updateRestaurant(restaurant);
    }

    public Dish getDish(int id, int restaurantId) {
        log.info("getDish with id={} in restaurant with id={}", id, restaurantId);
        checkNotFound(getWithLunch(restaurantId).getLunch().stream().filter(dish -> dish.getId() == id).count() != 0,
                String.format("restaurant id={} and dishId={}", restaurantId, id));
        return service.getDishById(id);
    }

    public Dish createDish(Dish dish, int restaurantId) {
        log.info("createDish {} in restaurant with id= {}", dish, restaurantId);
        checkNew(dish);
        dish.setRestaurant(service.getRestaurantById(restaurantId));
        return service.createDish(dish);
    }

    public void deleteDish(int restaurantId, int dishId) {
        log.info("deleteDish with id={} in restaurant with id={}", dishId, restaurantId);
        checkNotFound(getWithLunch(restaurantId).getLunch().stream().filter(dish -> dish.getId() == dishId).count() != 0,
                String.format("restaurant id={} and dishId={}", restaurantId, dishId));
        service.deleteDish(dishId);
    }

    public void updateDish(int restaurantId, Dish dish, int id) {
        log.info("updateDish {} with id={} in restaurant with id={}", dish, id, restaurantId);
        assureIdConsistent(dish, id);
        checkNotFound(getWithLunch(restaurantId).getLunch().stream().filter(d -> d.getId() == id).count() != 0,
                String.format("restaurant id={} and dishId={}", restaurantId, id));
        service.updateDish(dish);
    }

    public void endVoting() {
        log.info("endVoting");
        service.endVoting();
    }

    public void addRestaurantToVote(Restaurant restaurant) {
        log.info("addRestaurantToVote {}", restaurant);
        service.addRestaurantToVote(restaurant.getId());
    }

    public void addVoice(int userId, Restaurant restaurant, LocalTime time) throws TimeDelayException {
        log.info("addVoice user={} restaurant={} time={}");
        service.addVoice(userService.get(userId), restaurant, time);
    }


}