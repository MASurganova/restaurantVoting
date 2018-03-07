package ru.voting.util;

import ru.voting.model.Restaurant;
import ru.voting.model.RestaurantTO;
import ru.voting.model.User;
import ru.voting.service.UserService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class VotingUtil {

    private Map<LocalDate, RestaurantTO> historyVoting = new HashMap<>();

    private Set<RestaurantTO> currentRestaurants = new HashSet<>();

    private RestaurantTO currentChoice;

    private boolean votingEnded = false;

    private UserService users;

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
}
