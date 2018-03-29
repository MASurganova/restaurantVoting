package ru.voting;

import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.model.Role;
import ru.voting.model.User;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class TestData {
    public static final int USER_ID = 100000;
    public static final int ADMIN_ID = 100001;
    public static final Restaurant MY = new Restaurant(100000, "My", true, 1);
    public static final Restaurant OTHER = new Restaurant(100001, "Other");
    public static final Dish DISH_1 = new Dish(100000,"Чечевичный суп", 150, MY.getId());
    public static final Dish DISH_2 = new Dish(100001,"Салат с семгой", 250, MY.getId());
    public static final Dish DISH_3 = new Dish(100002,"Борщ", 250, OTHER.getId());
    public static final Dish DISH_4 = new Dish(100003,"Оливье", 150, OTHER.getId());
    public static final Dish DISH_5 = new Dish(100004,"Овощное рагу", 200, OTHER.getId());
    public static final User USER = new User(USER_ID, "User", "user@yandex.ru",
            "password",Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com",
            "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "roles");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatchUser(actual, Arrays.asList(expected));
    }

    public static void assertMatchUser(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("roles").isEqualTo(expected);
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
}
