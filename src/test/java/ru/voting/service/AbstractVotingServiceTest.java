package ru.voting.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.util.exception.NotFoundException;
import ru.voting.util.exception.TimeDelayException;

import javax.validation.ConstraintViolationException;
import java.time.LocalTime;

import static ru.voting.TestData.*;

public abstract class AbstractVotingServiceTest extends AbstractServiceTest {

    @Autowired
    VotingService service;

    @Autowired
    UserService users;

    @Test
    public void getRestaurantById() throws Exception {
        assertMatch(service.getRestaurantById(MY.getId()), MY);
    }

    @Test(expected = NotFoundException.class)
    public void getRestaurantByIdNotFound() throws Exception {
        service.getRestaurantById(5);
    }

    @Test
    public void getRestaurantByName() throws Exception {
        assertMatch(service.getRestaurantByName("My"), MY);
    }

    @Test
    public void getAllRestaurants() throws Exception {
        assertMatch(service.getAllRestaurants(), MY, OTHER);
    }

    @Test
    public void updateRestaurant() throws Exception {
        Restaurant newRestaurant = new Restaurant(OTHER.getId(),"New");
        Restaurant updateRestaurant = service.updateRestaurant(newRestaurant);
        newRestaurant.setId(updateRestaurant.getId());
        assertMatch(service.getRestaurantById(OTHER.getId()), newRestaurant);
    }

    @Test(expected = NotFoundException.class)
    public void updateRestaurantNotFound() throws Exception {
        Restaurant newRestaurant = new Restaurant(5,"New");
        service.updateRestaurant(newRestaurant);
    }

    @Test
    public void createRestaurant() throws Exception {
        Restaurant newRestaurant = new Restaurant(null,"New");
        Restaurant createRestaurant = service.createRestaurant(newRestaurant);
        newRestaurant.setId(createRestaurant.getId());
        assertMatch(service.getAllRestaurants(), MY, newRestaurant, OTHER);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateNameCreateRestaurantTest() throws Exception {
        Restaurant newRestaurant = new Restaurant(null,"My");
        service.createRestaurant(newRestaurant);
    }

    @Test
    public void deleteRestaurant() throws Exception {
        service.deleteRestaurant(OTHER.getId());
        assertMatch(service.getAllRestaurants(), MY);
    }

    @Test(expected = NotFoundException.class)
    public void deleteRestaurantNotFound() throws Exception {
        service.deleteRestaurant(5);
    }

    @Test
    public void getDishById() throws Exception {
        assertMatch(service.getDishById(DISH_1.getId()), DISH_1);
    }

    @Test(expected = NotFoundException.class)
    public void getDishByIdNotFound() throws Exception {
        service.getDishById(5);
    }
}