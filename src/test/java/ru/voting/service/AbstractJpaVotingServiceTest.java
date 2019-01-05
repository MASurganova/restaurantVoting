package ru.voting.service;

import org.junit.Assert;
import org.junit.Test;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.util.exception.NotFoundException;
import ru.voting.util.exception.TimeDelayException;

import javax.validation.ConstraintViolationException;
import java.time.LocalTime;
import java.util.List;

import static ru.voting.TestData.*;

public abstract class AbstractJpaVotingServiceTest extends AbstractVotingServiceTest {

    @Test
    public void endVoting() throws Exception {
        service.endVoting();
          Assert.assertEquals(service.getVotingHistory().size(), 3);
        Assert.assertEquals(service.getCurrentRestaurants().size(), 0);
        Assert.assertFalse(service.getAllRestaurants().stream()
                .anyMatch(Restaurant::isEnabled));
        Assert.assertFalse(service.getAllRestaurants().stream()
                .anyMatch(restaurant -> restaurant.getVoters()!=0));
        Assert.assertEquals(users.getAll().stream()
                .filter(user -> user.getChoice() != null).count(), 0);
    }

    @Test
    public void startVoting() throws Exception {
        //to do later
    }

    @Test
    public void getHistoryVoting() throws Exception {
        //to do later
    }

    @Test
    public void getCurrentRestaurants() throws Exception {
        assertMatch(service.getCurrentRestaurants(), MY);
    }

    @Test
    public void getCurrentChoice() throws Exception {
        service.addRestaurantToVote(OTHER.getId());
        Assert.assertTrue(service.getRestaurantById(OTHER.getId()).isEnabled());
        service.addVoice(ADMIN_ID, OTHER.getId(), LocalTime.of(10,0));
        service.addVoice(USER_ID, OTHER.getId(), LocalTime.of(10,0));
        Restaurant newOther = new Restaurant(OTHER);
        newOther.setEnabled(true);
        newOther.setVoters(2);
        assertMatch(service.getCurrentChoice(), newOther);
        Assert.assertEquals(service.getRestaurantById(MY.getId()).getVoters(), 0);
    }

    @Test
    public void addVoice() throws Exception {
        service.addVoice(ADMIN_ID, MY.getId(), LocalTime.of(10,0));
        Assert.assertEquals(service.getRestaurantById(MY.getId()).getVoters(), 2);
    }

    @Test
    public void addVoiceNotEnabled() throws Exception {
        service.addVoice(ADMIN_ID, OTHER.getId(), LocalTime.of(10,0));
        Assert.assertEquals(service.getRestaurantById(OTHER.getId()).getVoters(), 0);
    }

    @Test
    public void addVoiceAgain() throws Exception {
        service.addRestaurantToVote(OTHER.getId());
        service.addVoice(USER_ID, OTHER.getId(), LocalTime.of(10,0));
        Assert.assertEquals(service.getRestaurantById(OTHER.getId()).getVoters(), 1);
        Assert.assertEquals(service.getRestaurantById(MY.getId()).getVoters(), 0);
    }

    @Test(expected = TimeDelayException.class)
    public void addVoiceTimeDelay() throws Exception {
        service.addVoice(ADMIN_ID, MY.getId(), LocalTime.of(19,0));
    }

    @Test
    public void addRestaurantToVote() throws Exception {
        service.addRestaurantToVote(OTHER.getId());
        assertMatch(service.getCurrentRestaurants(), MY, service.getRestaurantById(OTHER.getId()));
    }

    @Test
    public void createDish() throws Exception {
        Dish newDish = new Dish(null, "new", 120, MY);
        Dish createDish = service.createDish(newDish, MY.getId());
        newDish.setId(createDish.getId());
        assertMatch(service.getRestaurantByIdWithLunch(MY.getId()).getLunch(), DISH_1, DISH_2,newDish);
        Assert.assertEquals(service.getRestaurantByIdWithLunch(MY.getId()).getLunch().size(), 3);
    }

    @Test
    public void updateDish() throws Exception {
        Dish newDish = new Dish(DISH_1.getId(), "update", 120, MY);
        Dish updateDish = service.updateDish(newDish, MY.getId());
        assertMatch(service.getDishById(DISH_1.getId(), MY.getId()), updateDish);
    }

    @Test(expected = NotFoundException.class)
    public void updateDishNotFound() throws Exception {
        Dish updateDish = new Dish(5, "update", 120, MY);
        service.updateDish(updateDish, MY.getId());
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.createDish(new Dish(" ", 100), MY.getId()), ConstraintViolationException.class);
        validateRootCause(() -> service.createDish(new Dish("soap", 1), MY.getId()), ConstraintViolationException.class);
        validateRootCause(() -> service.createDish(new Dish("soap", 10001), MY.getId()), ConstraintViolationException.class);
        validateRootCause(() -> service.createRestaurant(new Restaurant(null, " ")), ConstraintViolationException.class);
    }

    @Test
    public void deleteDish() throws Exception {
        service.deleteDish(DISH_1.getId(), MY.getId());
        assertMatch(service.getRestaurantByIdWithLunch(MY.getId()).getLunch(), DISH_2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteDishNotFound() throws Exception {
        service.deleteDish(9, MY.getId());
    }

    @Test(expected = NotFoundException.class)
    public void deleteDishNotFoundInRestaurant() throws Exception {
        service.deleteDish(DISH_5.getId(), MY.getId());
    }

    @Test
    public void getAllRestaurantsWithLunch() throws Exception {
        List<Restaurant> restaurantsWithLunch = service.getAllRestaurantsWithLunch();
        Assert.assertEquals(restaurantsWithLunch.size(), 2);
        Assert.assertNotNull(restaurantsWithLunch.get(1).getLunch());
        assertMatch(restaurantsWithLunch.get(1).getLunch(), DISH_3, DISH_4, DISH_5);
    }
}