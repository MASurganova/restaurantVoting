package ru.voting;

import ru.voting.model.Restaurant;
import ru.voting.model.Dish;
import ru.voting.model.Role;
import ru.voting.model.User;

public class TestData {
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final Restaurant MY = new Restaurant(1, "My");
    public static final Restaurant OTHER = new Restaurant(2, "Other");
    public static final Dish DISH_1 = new Dish(1,"Чечевичный суп", 150, MY);
    public static final Dish DISH_2 = new Dish(2,"Салат с семгой", 250, MY);
    public static final Dish DISH_3 = new Dish(3,"Борщ", 250, OTHER);
    public static final Dish DISH_4 = new Dish(4,"Оливье", 150, OTHER);
    public static final Dish DISH_5 = new Dish(5,"Овощное рагу", 200, OTHER);
    public static final User USER = new User(1, "USER", "user@mail.ru",
            "password",Role.ROLE_USER);
    public static final User ADMIN = new User(2, "ADMIN", "admin@mail.ru",
            "password", Role.ROLE_ADMIN, Role.ROLE_USER);


}
