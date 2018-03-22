package ru.voting.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.repository.mock.InMemoryDishRepository;
import ru.voting.repository.mock.InMemoryHistoryRepository;
import ru.voting.repository.mock.InMemoryRestaurantRepository;
import ru.voting.repository.mock.InMemoryUserRepository;
import ru.voting.util.exception.NotFoundException;
import ru.voting.util.exception.TimeDelayException;

import java.time.LocalTime;
import java.util.Collection;

import static ru.voting.TestData.*;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringRunner.class)
public class InMemoryVotingServiceTest {

    @Autowired
    VotingService service;

    @Autowired
    private InMemoryUserRepository users;

    @Autowired
    private InMemoryRestaurantRepository restaurants;

    @Autowired
    private InMemoryDishRepository dishes;

    @Autowired
    private InMemoryHistoryRepository history;

    @Before
    public void setUp() throws Exception {
        users.init();
        restaurants.init();
        dishes.init();
        history.init();
    }

    @Test
    public void endVotingTest() {
        service.endVoting();
        Assert.assertEquals(service.getHistoryVoting().size(), 3);
        Assert.assertEquals(service.getCurrentRestaurants().size(), 0);
        Assert.assertEquals(service.getAllRestaurants().stream()
                .filter(restaurant -> restaurant.getVoters() != 0).count(), 0);
        Assert.assertEquals(users.getAll().stream()
                .filter(user -> user.getChoice() != null).count(), 0);
    }

    @Test
    public void getHistoryVotingTest() {
        Assert.assertEquals(service.getHistoryVoting().size(), 2);
    }

    @Test
    public void getCurrentRestaurants() {
        Collection<Restaurant> restaurants = service.getCurrentRestaurants();
        Assert.assertEquals(restaurants.size(), 1);
        Assert.assertEquals(restaurants.iterator().next(), MY);
    }

    @Test
    public void getCurrentChoiceTest() throws TimeDelayException {
        service.addRestaurantToVote(2);
        service.addVoice(users.get(1), service.getRestaurantById(1), LocalTime.of(10, 0,0));
        Assert.assertEquals(service.getCurrentChoice(), MY);
    }

    @Test
    public void addDishToLunchTest() {
        Dish newDish = new Dish("newDish", 100);
        service.addDishToLunch(service.getRestaurantById(1), newDish);
        Assert.assertEquals(service.getRestaurantById(1).getLunch().size(), 3);
        Assert.assertEquals(service.getDishById(6), newDish);
    }

    @Test(expected = NotFoundException.class)
    public void removeDishToLunchTest() throws NotFoundException {
    service.removeDishFromLunch(service.getRestaurantById(2), 5);
    Assert.assertEquals(service.getRestaurantById(2).getLunch().size(), 2);
    service.getDishById(5);
    }

    @Test
    public void addVoiceTest() throws TimeDelayException {
        service.addVoice(users.get(1), service.getRestaurantById(1), LocalTime.of(10,0));
        Assert.assertEquals(users.get(1).getChoice(), service.getRestaurantById(1));
        Assert.assertEquals(service.getRestaurantById(1).getVoters(), 1);
    }

    @Test(expected = TimeDelayException.class)
    public void addVoiceTimeDelayTest() throws TimeDelayException {
        service.addVoice(users.get(1), service.getRestaurantById(1), LocalTime.of(13,0));
    }

    @Test(expected = NotFoundException.class)
    public void addRestaurantToVoteNotFoundTest() throws NotFoundException {
        service.addRestaurantToVote(3);
    }

    @Test
    public void addRestaurantToVoteTest() throws NotFoundException {
        service.addRestaurantToVote(2);
        Assert.assertEquals(service.getCurrentRestaurants().size(), 2);
    }

    @Test
    public void getRestaurantByIdTest() throws NotFoundException {
        Assert.assertEquals(service.getRestaurantById(1), MY);
    }

    @Test(expected = NotFoundException.class)
    public void getRestaurantByIdNotFoundTest() throws NotFoundException {
        service.getRestaurantById(3);
    }

    @Test
    public void getRestaurantByNameTest() throws NotFoundException {
        Assert.assertEquals(service.getRestaurantByName("My"), MY);
    }

    @Test(expected = NotFoundException.class)
    public void getRestaurantByNameNotFoundTest() throws NotFoundException {
        service.getRestaurantByName("new");
    }

    @Test
    public void getAllRestaurants() {
        Assert.assertEquals(service.getAllRestaurants().size(), 2);
    }

    @Test
    public void updateRestaurantTest() throws NotFoundException {
        Restaurant newRestaurant = new Restaurant(1, "New");
        service.updateRestaurant(newRestaurant);
        Assert.assertEquals(service.getRestaurantById(1), newRestaurant);
    }

    @Test(expected = NotFoundException.class)
    public void updateRestaurantNotFoundTest() throws NotFoundException {
        Restaurant newRestaurant = new Restaurant(3, "New");
        service.updateRestaurant(newRestaurant);
    }

    @Test
    public void createRestaurantTest() {
        Restaurant newRestaurant = new Restaurant(null, "New");
        service.createRestaurant(newRestaurant);
        Assert.assertEquals(service.getAllRestaurants().size(), 3);
        Assert.assertEquals(service.getRestaurantById(3), newRestaurant);
    }

    @Test(expected = NotFoundException.class)
    public void deleteRestaurantTest() throws NotFoundException {
        service.deleteRestaurant(1);
        Assert.assertEquals(service.getAllRestaurants().size(), 1);
        service.getRestaurantById(1);

    }

    @Test(expected = NotFoundException.class)
    public void deleteRestaurantNotFoundTest() throws NotFoundException {
        service.deleteRestaurant(3);

    }

    @Test
    public void getDishByIdTest() throws NotFoundException {
        Assert.assertEquals(service.getDishById(4), DISH_4);
    }

    @Test(expected = NotFoundException.class)
    public void getDishByIdNotFoundTest() throws NotFoundException {
        service.getDishById(6);
    }

    @Test
    public void updateDishTest() throws NotFoundException {
        Dish newDish = new Dish(3, "newDish", 120);
        service.updateDish(newDish);
        Assert.assertEquals(service.getDishById(3), newDish);
    }

    @Test(expected = NotFoundException.class)
    public void updateDishNotFoundTest() throws NotFoundException {
        Dish newDish = new Dish(7, "newDish", 120);
        service.updateDish(newDish);
    }

    @Test
    public void createDishTest() {
        Dish newDish = new Dish("newDish", 120);
        service.createDish(newDish);
        System.out.println(newDish);
        Assert.assertEquals(service.getDishById(6), newDish);
    }

    @Test(expected = NotFoundException.class)
    public void deleteDishTest() throws NotFoundException {
        service.deleteDish(1);
        service.getDishById(1);
    }

    @Test(expected = NotFoundException.class)
    public void deleteDishTestNotFound() throws NotFoundException {
        service.deleteDish(7);
    }
}
