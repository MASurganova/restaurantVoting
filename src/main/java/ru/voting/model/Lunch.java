package ru.voting.model;

import java.util.List;

public class Lunch extends AbstractBaseEntity {

    private List<Dish> dishes;

    public Lunch(Integer id, List<Dish> dishes) {
        super(id);
        this.dishes = dishes;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public void addDish(Dish dish) {
        dishes.add(dish);
    }

    public void removeDish(Dish dish) {
        dishes.remove(dish);
    }
}
