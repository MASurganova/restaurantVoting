package ru.voting.web.json;

import org.junit.Test;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.model.User;

import java.util.Arrays;
import java.util.List;

import static ru.voting.TestData.*;

public class JsonUtilTest {

    @Test
    public void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(MY);
        System.out.println(json);
        Restaurant restaurant = JsonUtil.readValue(json,Restaurant.class);
        assertMatch(restaurant, MY);
    }

    @Test
    public void testReadWriteValues() throws Exception {
        String json = JsonUtil.writeValue(Arrays.asList(MY, OTHER));
        System.out.println(json);
        List<Restaurant> restaurants = JsonUtil.readValues(json, Restaurant.class);
        assertMatch(restaurants, MY, OTHER);
    }

    @Test
    public void testReadWriteValueUser() throws Exception {
        String json = JsonUtil.writeValue(USER);
        System.out.println(json);
        User user = JsonUtil.readValue(json, User.class);
        assertMatch(user, USER);
    }

    @Test
    public void testReadWriteValueDish() throws Exception {
        String json = JsonUtil.writeValue(DISH_1);
        System.out.println(json);
        Dish dish = JsonUtil.readValue(json, Dish.class);
        assertMatch(dish, DISH_1);
    }

    @Test
    public void testReadWriteValuesDish() throws Exception {
        String json = JsonUtil.writeValue(Arrays.asList(DISH_1, DISH_2));
        System.out.println(json);
        List<Dish> dishes = JsonUtil.readValues(json, Dish.class);
        assertMatch(dishes, DISH_1, DISH_2);
    }
}