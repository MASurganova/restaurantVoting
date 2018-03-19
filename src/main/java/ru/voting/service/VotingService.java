package ru.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.model.User;
import ru.voting.repository.DishRepository;
import ru.voting.repository.HistoryRepository;
import ru.voting.repository.RestaurantRepository;
import ru.voting.repository.UserRepository;
import ru.voting.util.exception.TimeDelayException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VotingService {

    private boolean votingEnded = false;

    @Autowired
    private UserRepository users;

    @Autowired
    private RestaurantRepository restaurants;

    @Autowired
    private DishRepository dishes;

    @Autowired
    private HistoryRepository history;

    public void endVoting() {
        Restaurant currentChoice = getCurrentChoice();
        history.addInHistory(LocalDate.now(), currentChoice);
        votingEnded = false;
        restaurants.getAll().forEach(restaurants::updateVoters);
        restaurants.getAll().forEach(r -> restaurants.disabled(r));
        users.getAll().stream().map(User::getEmail).forEach(email -> sendEmail(email,
                String.format("Голосование за ресторан, где мы будем обедать завершено, выбран ресторан - %s"
                        , currentChoice.getName())));
        users.getAll().forEach(u -> users.setChoice(u, null));
    }

    public void startVoting() {
        votingEnded = false;
        users.getAll().stream().map(User::getEmail).forEach(email -> sendEmail(email,
                "Голосование за ресторан, где мы будем обедать начато, проголосуйте пожалуйста"));
    }

//    Нужно реализовать рассылку письма по email с переданным текстом
    private void sendEmail(String email, String s) {}

    public Map<LocalDate, Restaurant> getHistoryVoting() {
        return history.getHistory();
    }

    public Set<Restaurant> getCurrentRestaurants() {
        return restaurants.getAll().stream().filter(Restaurant::isEnabled).collect(Collectors.toSet());
    }

    public Restaurant getCurrentChoice() {
        Set<Restaurant> currentRestorants = getCurrentRestaurants();
        int maxVoters = currentRestorants.stream().map(Restaurant::getVoters)
                .max(Integer::compareTo).orElseGet(null);
        return currentRestorants.stream().filter(restaurant -> restaurant.getVoters() == maxVoters)
                .findFirst().orElseGet(null);
    }

    public void addDishToLunch(Restaurant restaurant, Dish dish) {
        restaurant.addDish(dish);
        dish.setRestaurant(restaurant);
        restaurants.save(restaurant);
        dishes.save(dish);
    }

    public void removeDishFromLunch(Restaurant restaurant, int dishId) {
        Dish dish = dishes.get(dishId);
        restaurant.removeDish(dish);
        restaurants.save(restaurant);
        dishes.delete(dishId);
    }

    public void addVoice(User user, Restaurant restaurant) throws TimeDelayException {
        LocalTime time = LocalTime.now();
        if (time.isAfter(LocalTime.of(12, 0))) throw new TimeDelayException("attempt to change the choice after 11:00");
        if (user.getChoice() == null || !restaurant.equals(user.getChoice())) {
            if (user.getChoice() != null)
                restaurants.removeVoter(user.getChoice());
            users.setChoice(user, restaurant);
            restaurants.addVoter(restaurant);
        }
    }

    public void addVoice(int userId, int restaurantId) throws TimeDelayException {
        addVoice(users.get(userId), restaurants.get(restaurantId));
    }
}
