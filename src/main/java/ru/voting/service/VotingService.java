package ru.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.model.RestaurantTO;
import ru.voting.model.User;
import ru.voting.repository.DishRepository;
import ru.voting.repository.RestaurantRepository;
import ru.voting.repository.UserRepository;
import ru.voting.util.exception.TimeDelayException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VotingService {

    private Map<LocalDate, RestaurantTO> historyVoting = new HashMap<>();

    private Set<RestaurantTO> currentRestaurants = new HashSet<>();

    private RestaurantTO currentChoice;

    private boolean votingEnded = false;

    @Autowired
    private UserRepository users;

    @Autowired
    private RestaurantRepository restaurants;

    @Autowired
    private DishRepository dishes;

    public void endVoting() {
        int maxVoters = currentRestaurants.stream().map(RestaurantTO::getVoters)
                .max(Integer::compareTo).orElseGet(null);
        currentChoice = currentRestaurants.stream().filter(restaurantTO -> restaurantTO.getVoters() == maxVoters)
                .findFirst().orElseGet(null);
        historyVoting.put(LocalDate.now(), currentChoice);
        votingEnded = true;
        currentRestaurants.clear();
        users.getAll().stream().map(User::getEmail).forEach(email -> sendEmail(email,
                String.format("Голосование за ресторан, где мы будем обедать завершено, выбран ресторан - %s"
                        , currentChoice.getName())));
    }

    public void startVoting() {
        votingEnded = false;
        users.getAll().stream().map(User::getEmail).forEach(email -> sendEmail(email,
                "Голосование за ресторан, где мы будем обедать начато, проголосуйте пожалуйста"));
    }

//    Нужно реализовать рассылку письма по email с переданным текстом
    private void sendEmail(String email, String s) {}

    public void addRestaurant(Restaurant restaurant) {
        currentRestaurants.add(new RestaurantTO(restaurant));
    }

    public void removeRestaurant(Restaurant restaurant) {
        this.currentRestaurants = currentRestaurants.stream().filter(restaurantTO -> !restaurantTO.getName().equals(restaurant.getName()))
        .collect(Collectors.toSet());

    }

    public Map<LocalDate, RestaurantTO> getHistoryVoting() {
        return historyVoting;
    }

    public Set<RestaurantTO> getCurrentRestaurants() {
        if (votingEnded) return currentRestaurants;
        else return null;
    }

    public RestaurantTO getCurrentChoice() {
        return currentChoice;
    }

    public void setCurrentChoice(RestaurantTO currentChoice) {
        this.currentChoice = currentChoice;
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

    public void addVoice(User user, RestaurantTO restaurant) throws TimeDelayException {
        LocalTime time = LocalTime.now();
        if (time.isAfter(LocalTime.of(11, 0))) throw new TimeDelayException("attempt to change the choice after 11:00");
        if (user.getChoice() == null || !restaurant.getName().equals(user.getChoice().getName())) {
            if (user.getChoice() != null) {
                RestaurantTO choise = currentRestaurants.stream().filter(r -> r.getName().equals(user.getChoice().getName()))
                        .findFirst().orElse(null);
                if (choise != null) choise.removeVoter();
            }
            user.setChoice(restaurants.getByName(restaurant.getName()));
            users.save(user);
            restaurant.addVoter();
        }
    }
}
