package ru.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.model.User;
import ru.voting.repository.DishRepository;
import ru.voting.repository.HistoryRepository;
import ru.voting.repository.RestaurantRepository;
import ru.voting.repository.UserRepository;
import ru.voting.to.DishTo;
import ru.voting.util.DishUtil;
import ru.voting.util.ValidationUtil;
import ru.voting.util.exception.NotFoundException;
import ru.voting.util.exception.TimeDelayException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static ru.voting.util.ValidationUtil.checkNotFound;
import static ru.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VotingService {

    @Autowired
    private UserRepository users;

    @Autowired
    private RestaurantRepository restaurants;

    @Autowired
    private DishRepository dishes;

    @Autowired
    private HistoryRepository history;

    @CacheEvict(value = "users", allEntries = true)
    public void endVoting() {
        Restaurant currentChoice = getCurrentChoice();
        history.addInHistory(LocalDate.now(), currentChoice);
        restaurants.getAll().forEach(r-> restaurants.updateVoters(r.getId()));
        restaurants.getAll().forEach(r -> restaurants.disabled(r.getId()));
        users.getAll().stream().map(User::getEmail).forEach(email -> sendEmail(email,
                String.format("Голосование за ресторан, где мы будем обедать завершено, выбран ресторан - %s"
                        , currentChoice.getName())));
        users.getAll().forEach(u -> users.save(u, null));
    }

    public void startVoting() {
        users.getAll().stream().map(User::getEmail).forEach(email -> sendEmail(email,
                "Голосование за ресторан, где мы будем обедать, начато - проголосуйте пожалуйста"));
    }

//    Нужно реализовать рассылку письма по email с переданным текстом
    private void sendEmail(String email, String s) {}

    public Map<LocalDate, Restaurant> getHistoryVoting() {
        return history.getHistory();
    }

    public List<Restaurant> getCurrentRestaurants() {
        return restaurants.getEnabledRestaurants();
    }

    public Restaurant getCurrentChoice() {
       return getCurrentRestaurants().stream().max(Comparator.comparingInt(Restaurant::getVoters)).orElse(null);
    }

    private void addVoice(User user, Restaurant restaurant, LocalTime time) throws TimeDelayException {
        if (time == null) time = LocalTime.now();
        if (time.isAfter(LocalTime.of(11, 0))) throw new TimeDelayException("attempt to change the choice after 11:00");
        if ((user.getChoice() == null || !restaurant.equals(user.getChoice())) && restaurant.isEnabled()) {
            if (user.getChoice() != null)
                restaurants.removeVoter(user.getChoice().getId());
            users.save(user, restaurant.getId());
            restaurants.addVoter(restaurant.getId());
        }
    }

    @CacheEvict(value = "users", allEntries = true)
    public void addVoice(int userId, int restaurantId) throws TimeDelayException, NotFoundException {
        addVoice(userId, restaurantId, null);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void addVoice(int userId, int restaurantId, LocalTime time) throws TimeDelayException {
        addVoice(ValidationUtil.checkNotFoundWithId(users.getWithChoice(userId), userId),
                checkNotFoundWithId(restaurants.get(restaurantId), restaurantId), time);
    }

    public void addRestaurantToVote(int id) throws NotFoundException {
        checkNotFoundWithId(restaurants.get(id), id);
        restaurants.enabled(id);
    }

    public Restaurant getRestaurantById(int id) throws NotFoundException {
        return checkNotFoundWithId(restaurants.get(id), id);
    }

    public Restaurant getRestaurantByName(String name) throws NotFoundException {
        return checkNotFound(restaurants.getByName(name), "name=" + name);
    }

    public Restaurant getRestaurantByIdWithLunch(int id) throws NotFoundException {
        return checkNotFoundWithId(restaurants.getWithLunch(id), id);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurants.getAll();
    }

    public List<Restaurant> getAllRestaurantsWithLunch() {
        return restaurants.getAllWithLunch();
    }

    public void updateRestaurant(Restaurant restaurant) throws NotFoundException {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurants.get(restaurant.getId()), restaurant.getId());
        restaurants.update(restaurant);
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurants.save(restaurant);
    }

    public void deleteRestaurant(int id) throws NotFoundException {
        checkNotFoundWithId(restaurants.delete(id), id);
    }

    public Dish getDishById(int id, int restaurantId) throws NotFoundException {
        return checkNotFoundWithId(dishes.get(id, restaurantId), id);
    }

    public Dish createDish(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        return dishes.save(dish, restaurantId);
    }

    public Dish updateDish(Dish dish, int restaurantId) throws NotFoundException {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(dishes.get(dish.getId(), restaurantId), dish.getId());
        return dishes.save(dish, restaurantId);
    }

    public Dish updateDish(DishTo dishTo, int restaurantId) throws NotFoundException {
        Assert.notNull(dishTo, "dishTo must not be null");
        Dish dish = getDishById(dishTo.getId(), restaurantId);
        Assert.notNull(dish, "dish must not be null");
        return checkNotFoundWithId(dishes.save(DishUtil.updateFromTo(dish, dishTo), restaurantId), dishTo.getId());
    }

    public void deleteDish(int id, int restaurantId) throws NotFoundException {
        checkNotFoundWithId(dishes.delete(id, restaurantId), id);
    }

}
