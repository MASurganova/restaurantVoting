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

public abstract class AbstractJpaVotingServiceTest extends AbstractVotingServiceTest {

    @Test
    public void endVoting() throws Exception {
        service.endVoting();
//        Assert.assertEquals(service.getHistoryVoting().size(), 3);
        Assert.assertEquals(service.getCurrentRestaurants().size(), 0);
        Assert.assertEquals(service.getAllRestaurants().stream()
                .filter(restaurant -> restaurant.getVoters() != 0).count(), 0);
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
        Assert.assertEquals(service.getRestaurantById(OTHER.getId()).isEnabled(), true);
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
//        after add choice(restaurant) in user
//        Assert.assertEquals(service.getRestaurantById(MY.getId()).getVoters(), 0);
    }

    @Test(expected = TimeDelayException.class)
    public void addVoiceTimeDelay() throws Exception {
        service.addVoice(ADMIN_ID, MY.getId(), LocalTime.of(19,0));
    }

    @Test
    public void addRestaurantToVote() throws Exception {
        service.addRestaurantToVote(OTHER.getId());
        Restaurant enabledOther = new Restaurant(OTHER);
        enabledOther.setEnabled(true);
        assertMatch(service.getCurrentRestaurants(), MY, enabledOther);
    }

    @Test
    public void createDish() throws Exception {
        Dish newDish = new Dish(null, "new", 120, MY);
        Dish createDish = service.createDish(newDish);
        newDish.setId(createDish.getId());
        assertMatch(service.getRestaurantByIdWithLunch(MY.getId()).getLunch(), DISH_1, DISH_2,newDish);
        Assert.assertEquals(service.getRestaurantByIdWithLunch(MY.getId()).getLunch().size(), 3);
    }

    @Test
    public void updateDish() throws Exception {
        Dish newDish = new Dish(DISH_1.getId(), "update", 120, MY);
        Dish updateDish = service.updateDish(newDish);
        assertMatch(service.getDishById(DISH_1.getId()), updateDish);
    }

    @Test(expected = NotFoundException.class)
    public void updateDishNotFound() throws Exception {
        Dish updateDish = new Dish(5, "update", 120, MY);
        service.updateDish(updateDish);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.createDish(new Dish(" ", 100)), ConstraintViolationException.class);
        validateRootCause(() -> service.createDish(new Dish("soap", 1)), ConstraintViolationException.class);
        validateRootCause(() -> service.createDish(new Dish("soap", 10001)), ConstraintViolationException.class);
        validateRootCause(() -> service.createRestaurant(new Restaurant(null, " ")), ConstraintViolationException.class);
    }

    @Test
    public void deleteDish() throws Exception {
        service.deleteDish(DISH_1.getId());
        assertMatch(service.getRestaurantByIdWithLunch(MY.getId()).getLunch(), DISH_2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteDishNotFound() throws Exception {
        service.deleteDish(9);
    }
}