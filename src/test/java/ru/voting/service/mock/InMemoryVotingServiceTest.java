package ru.voting.service.mock;

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
import ru.voting.service.VotingService;
import ru.voting.util.exception.NotFoundException;
import ru.voting.util.exception.TimeDelayException;

import java.time.LocalTime;
import java.util.Collection;

import static ru.voting.TestData.*;
import static ru.voting.model.AbstractBaseEntity.START_SEQ;

@ContextConfiguration({"classpath:spring/mock.xml",
        "classpath:spring/spring-app.xml"} )
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
    public void getHistoryVotingTest() {
        Assert.assertEquals(service.getHistoryVoting().size(), 2);
    }

    @Test
    public void getCurrentRestaurants() {
        Collection<Restaurant> restaurants = service.getCurrentRestaurants();
        Assert.assertEquals(restaurants.size(), 1);
        Assert.assertEquals(restaurants.iterator().next(), service.getRestaurantByName("My"));
        service.addRestaurantToVote(OTHER.getId());
        restaurants = service.getCurrentRestaurants();
        Assert.assertEquals(restaurants.size(), 2);
    }

    @Test
    public void getCurrentChoiceTest() throws TimeDelayException {
        Assert.assertEquals(service.getCurrentChoice(), service.getRestaurantById(MY.getId()));
    }

    @Test
    public void addVoiceTest() throws TimeDelayException {
        service.addVoice(ADMIN_ID, MY.getId(), LocalTime.of(10, 0));
        Assert.assertEquals(users.get(ADMIN_ID).getChoice(), service.getRestaurantByName("My"));
        Assert.assertEquals(service.getRestaurantById(START_SEQ).getVoters(), 2);
    }

    @Test(expected = TimeDelayException.class)
    public void addVoiceTimeDelayTest() throws TimeDelayException {
        service.addVoice(ADMIN_ID, MY.getId(), LocalTime.of(20,0));
        Assert.assertEquals(service.getRestaurantById(MY.getId()).getVoters(), 2);
        Assert.assertEquals(users.getWithChoice(ADMIN_ID).getChoice().getName(), MY.getName());
    }

    @Test(expected = NotFoundException.class)
    public void addRestaurantToVoteNotFoundTest() throws NotFoundException {
        service.addRestaurantToVote(2);
    }

    @Test
    public void addRestaurantToVoteTest() throws NotFoundException {
        service.addRestaurantToVote(START_SEQ + 1);
        Assert.assertEquals(service.getCurrentRestaurants().size(), 2);
    }

    @Test
    public void getRestaurantByIdTest() throws NotFoundException {
        Assert.assertEquals(service.getRestaurantById(START_SEQ), service.getRestaurantByName("My"));
    }

    @Test(expected = NotFoundException.class)
    public void getRestaurantByIdNotFoundTest() throws NotFoundException {
        service.getRestaurantById(2);
    }

    @Test
    public void getRestaurantByNameTest() throws NotFoundException {
        Assert.assertEquals(service.getRestaurantByName("My"), service.getRestaurantById(MY.getId()));
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
        Restaurant newRestaurant = new Restaurant(START_SEQ, "New");
        service.updateRestaurant(newRestaurant);
        Assert.assertEquals(service.getRestaurantById(START_SEQ).getName(), newRestaurant.getName());
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
        Assert.assertEquals(service.getRestaurantByName("New"), newRestaurant);
    }

    @Test(expected = NotFoundException.class)
    public void deleteRestaurantTest() throws NotFoundException {
        service.deleteRestaurant(START_SEQ);
        Assert.assertEquals(service.getAllRestaurants().size(), 1);
        service.getRestaurantById(START_SEQ);

    }

    @Test(expected = NotFoundException.class)
    public void deleteRestaurantNotFoundTest() throws NotFoundException {
        service.deleteRestaurant(3);

    }

    @Test
    public void getDishByIdTest() throws NotFoundException {
        Assert.assertEquals(service.getDishById(START_SEQ + 5), DISH_4);
    }

    @Test(expected = NotFoundException.class)
    public void getDishByIdNotFoundTest() throws NotFoundException {
        service.getDishById(6);
    }

    @Test
    public void updateDishTest() throws NotFoundException {
        Dish newDish = new Dish(START_SEQ + 2, "newDish", 120);
        service.updateDish(newDish);
        Assert.assertEquals(service.getDishById(START_SEQ + 2), newDish);
    }

    @Test(expected = NotFoundException.class)
    public void updateDishNotFoundTest() throws NotFoundException {
        Dish newDish = new Dish(7, "newDish", 120);
        service.updateDish(newDish);
    }

    @Test(expected = NotFoundException.class)
    public void deleteDishTest() throws NotFoundException {
        service.deleteDish(START_SEQ + 2);
        service.getDishById(START_SEQ + 2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteDishTestNotFound() throws NotFoundException {
        service.deleteDish(7);
    }
}
