package ru.voting.util;

import ru.voting.model.Dish;
import ru.voting.model.Restaurant;

public class RestaurantUtil {
    public static Restaurant my = new Restaurant(null, "My");
    public static Restaurant other = new Restaurant(null, "Other");
    public static Dish dish1 = new Dish(null,"Чечевичный суп", 150, my);
    public static Dish dish2 = new Dish(null,"Салат с семгой", 250, my);
    public static Dish dish3 = new Dish(null,"Борщ", 250, other);
    public static Dish dish4 = new Dish(null,"Оливье", 150, other);
    public static Dish dish5 = new Dish(null,"Овощное рагу", 200, other);

    static {
        my.addDish(dish1);
        my.addDish(dish2);
        other.addDish(dish3);
        other.addDish(dish4);
        other.addDish(dish5);
    }

}
