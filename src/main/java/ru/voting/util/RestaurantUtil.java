package ru.voting.util;

import ru.voting.model.Dish;
import ru.voting.model.Restaurant;

public class RestaurantUtil {
    public static final Restaurant MY = new Restaurant(null, "My");
    public static final Restaurant OTHER = new Restaurant(null, "Other");
    public static final Dish DISH_1 = new Dish(null,"Чечевичный суп", 150, MY);
    public static final Dish DISH_2 = new Dish(null,"Салат с семгой", 250, MY);
    public static final Dish DISH_3 = new Dish(null,"Борщ", 250, OTHER);
    public static final Dish DISH_4 = new Dish(null,"Оливье", 150, OTHER);
    public static final Dish DISH_5 = new Dish(null,"Овощное рагу", 200, OTHER);



}
