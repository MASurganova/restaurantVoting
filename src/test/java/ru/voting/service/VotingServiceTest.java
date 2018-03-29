package ru.voting.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.util.exception.NotFoundException;
import ru.voting.util.exception.TimeDelayException;

import java.time.LocalTime;

import static ru.voting.TestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class VotingServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    VotingService service;

    @Autowired
    UserService users;

    @Test
    public void endVoting() throws Exception {
        service.endVoting();
//        Assert.assertEquals(service.getHistoryVoting().size(), 3);
        Assert.assertEquals(service.getCurrentRestaurants().size(), 0);
        Assert.assertEquals(service.getAllRestaurants().stream()
                .filter(restaurant -> restaurant.getVoters() != 0).count(), 0);
//        after add choice(restaurant) in user
//        Assert.assertEquals(users.getAll().stream()
//                .filter(user -> user.getChoice() != null).count(), 0);
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
        service.addVoice(ADMIN_ID, OTHER.getId());
        service.addVoice(USER_ID, OTHER.getId());
        Restaurant newOther = new Restaurant(OTHER);
        newOther.setEnabled(true);
        newOther.setVoters(2);
        assertMatch(service.getCurrentChoice(), newOther);
//        after add choice(restaurant) in user
//        Assert.assertEquals(service.getRestaurantById(MY.getId()).getVoters(), 0);
    }

    @Test
    public void addDishToLunch() throws Exception {
        //to do later
        assertMatch(service.getRestaurantByName("My"), MY);
    }

    @Test
    public void removeDishFromLunch() throws Exception {
        //to do later
        assertMatch(service.getRestaurantByName("My"), MY);
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
//        after add choice(restaurant) in user
//        Assert.assertEquals(service.getRestaurantById(MY.getId()).getVoters(), 0);
    }

    @Test(expected = TimeDelayException.class)
    public void addVoiceTimeDelay() throws Exception {
        service.addVoice(ADMIN_ID, MY.getId(), LocalTime.of(13,0));
    }

    @Test
    public void addRestaurantToVote() throws Exception {
        service.addRestaurantToVote(OTHER.getId());
        Restaurant enabledOther = new Restaurant(OTHER);
        enabledOther.setEnabled(true);
        assertMatch(service.getCurrentRestaurants(), MY, enabledOther);
    }

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
        service.updateRestaurant(newRestaurant);
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
        service.createRestaurant(newRestaurant);
        assertMatch(service.getAllRestaurants(), MY, newRestaurant, OTHER);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateNameCreateRestaurantTest() throws Exception {
        Restaurant newRestaurant = new Restaurant(null,"My");
        service.createRestaurant(newRestaurant);
    }

    @Test
    public void deleteRestaurant() throws Exception {
        service.deleteRestaurant(MY.getId());
        assertMatch(service.getAllRestaurants(), OTHER);
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

    @Test
    public void updateDish() throws Exception {
        Dish updateDish = new Dish(DISH_1.getId(), "update", 120, MY.getId());
        service.updateDish(updateDish);
        assertMatch(service.getDishById(DISH_1.getId()), updateDish);
    }

    @Test(expected = NotFoundException.class)
    public void updateDishNotFound() throws Exception {
        Dish updateDish = new Dish(5, "update", 120, MY.getId());
        service.updateDish(updateDish);
    }

    @Test
    public void deleteDish() throws Exception {
        service.deleteDish(100000);
        assertMatch(service.getAllDishes(), DISH_2, DISH_3, DISH_4, DISH_5);
    }

    @Test(expected = NotFoundException.class)
    public void deleteDishNotFound() throws Exception {
        service.deleteDish(100009);
    }

}