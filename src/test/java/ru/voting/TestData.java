package ru.voting;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.model.Role;
import ru.voting.model.User;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.voting.model.AbstractBaseEntity.START_SEQ;
import static ru.voting.web.json.JsonUtil.writeIgnoreProps;

public class TestData {
    public static final int USER_ID = START_SEQ+7;
    public static final int ADMIN_ID = USER_ID + 1;
    public static final Restaurant MY = new Restaurant(START_SEQ , "My", true, 1);
    public static final Restaurant OTHER = new Restaurant(START_SEQ + 1, "Other");
    public static final Dish DISH_1 = new Dish(START_SEQ + 2,"Чечевичный суп", 150, MY);
    public static final Dish DISH_2 = new Dish(START_SEQ + 3,"Салат с семгой", 250, MY);
    public static final Dish DISH_3 = new Dish(START_SEQ + 4,"Борщ", 250, OTHER);
    public static final Dish DISH_4 = new Dish(START_SEQ + 5,"Оливье", 150, OTHER);
    public static final Dish DISH_5 = new Dish(START_SEQ + 6,"Овощное рагу", 200, OTHER);
    public static final User USER = new User(USER_ID, "User", "user@yandex.ru",
            "password",Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com",
            "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "choice");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatchUser(actual, Arrays.asList(expected));
    }

    public static void assertMatchUser(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "choice").isEqualTo(expected);
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "lunch");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatchRestaurant(actual, Arrays.asList(expected));
    }

    public static void assertMatchRestaurant(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("lunch").isEqualTo(expected);
    }

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatchDish(actual, Arrays.asList(expected));
    }

    public static void assertMatchDish(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "registered"));
    }

    public static ResultMatcher contentJson(User expected) {
        return content().json(writeIgnoreProps(expected, "registered"));
    }

    public static ResultMatcher contentJson(Restaurant expected) {
        return content().json(writeIgnoreProps(expected, "lunch"));
    }

    public static ResultMatcher contentJson(Restaurant ... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "lunch"));
    }

    public static ResultMatcher contentJson(Dish expected) {
        return content().json(writeIgnoreProps(expected, "restaurant"));
    }

    public static ResultMatcher contentJson(Dish ... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "restaurant"));
    }
}
