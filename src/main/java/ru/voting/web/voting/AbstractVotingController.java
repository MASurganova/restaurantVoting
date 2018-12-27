package ru.voting.web.voting;

import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.to.DishTo;
import ru.voting.util.exception.TimeDelayException;
import ru.voting.web.AbstractController;

import java.time.LocalTime;
import java.util.List;

import static ru.voting.util.ValidationUtil.*;

public abstract class AbstractVotingController extends AbstractController {

    public List<Restaurant> getAllRestaurants() {
        log.info("getAllRestaurants");
        return votingService.getAllRestaurants();
    }

    public List<Restaurant> getEnabledRestaurants() {
        log.info("getEnabledRestaurants");
        return votingService.getCurrentRestaurants();
    }

    public Restaurant getChoice() {
        log.info("getChoice");
        return votingService.getCurrentChoice();
    }

    public List<Restaurant> getVoting() {
        log.info("getVoting");
        return votingService.getCurrentRestaurants();
    }

    public Restaurant get(int id) {
        log.info("get {}", id);
        return votingService.getRestaurantById(id);
    }

    public Restaurant getWithLunch(int id) {
        log.info("getWithLunch {}", id);
        return votingService.getRestaurantByIdWithLunch(id);
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return votingService.createRestaurant(restaurant);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        votingService.deleteRestaurant(id);
    }

    public void update(Restaurant restaurant, int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        Restaurant updated = getWithLunch(id);
        updated.setName(restaurant.getName());
        votingService.updateRestaurant(updated);
    }


    public Dish getDish(int id, int restaurantId) {
        log.info("getDish with id={} in restaurant with id={}", id, restaurantId);
        checkDishInRestaurant(id, restaurantId);
        return votingService.getDishById(id);
    }

    public Dish createDish(Dish dish, int restaurantId) {
        log.info("createDish {} in restaurant with id= {}", dish, restaurantId);
        checkNew(dish);
        dish.setRestaurant(votingService.getRestaurantById(restaurantId));
        return votingService.createDish(dish);
    }

    public void deleteDish(int restaurantId, int dishId) {
        log.info("deleteDish with id={} in restaurant with id={}", dishId, restaurantId);
        checkDishInRestaurant(dishId, restaurantId);
        votingService.deleteDish(dishId);
    }

    public void updateDish(int restaurantId, Dish dish, int id) {
        log.info("updateDish {} with id={} in restaurant with id={}", dish, id, restaurantId);
        assureIdConsistent(dish, id);
        checkDishInRestaurant(id, restaurantId);
        dish.setRestaurant(votingService.getRestaurantById(restaurantId));
        votingService.updateDish(dish);
    }

    public void updateDish(int restaurantId, DishTo dishTo, int id) {
        log.info("updateDish {} with id={} in restaurant with id={}", dishTo, id, restaurantId);
        assureIdConsistent(dishTo, id);
        checkDishInRestaurant(id, restaurantId);
        votingService.updateDish(dishTo);
    }

    public void endVoting() {
        log.info("endVoting");
        votingService.endVoting();
    }

    public void addRestaurantToVote(int id) {
        log.info("addRestaurantToVote with id={}", id);
        votingService.addRestaurantToVote(id);
    }

    public void addVoice(int userId, Restaurant restaurant, LocalTime time) throws TimeDelayException {
        log.info("addVoice user={} restaurant={} time={}");
        votingService.addVoice(userService.get(userId), restaurant, time);
    }

    private void checkDishInRestaurant(int id, int restaurantId) {
        checkNotFound(getWithLunch(restaurantId).getLunch().stream().anyMatch(dish -> dish.getId() == id),
                String.format("restaurant id=%s and dishId=%s", restaurantId, id));
    }
}