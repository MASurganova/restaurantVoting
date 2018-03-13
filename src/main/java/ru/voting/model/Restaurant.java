package ru.voting.model;

import java.util.List;

public class Restaurant extends AbstractNamedEntity {

    private List<Dish> lunch;
    private int totalPrice;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, List<Dish> lunch) {
        this(id, name);
        this.lunch = lunch;
        totalPrice = lunch.stream().mapToInt(Dish::getPrice).sum();
    }

    public void addDish(Dish dish) {
        this.lunch.add(dish);
        totalPrice = lunch.stream().mapToInt(Dish::getPrice).sum();
    }

    public void removeDish(Dish dish) {
        this.lunch.remove(dish);
        totalPrice = lunch.stream().mapToInt(Dish::getPrice).sum();
    }

    public List<Dish> getLunch() {
        return lunch;
    }

    public void setLunch(List<Dish> lunch) {
        this.lunch = lunch;
        totalPrice = lunch.stream().mapToInt(Dish::getPrice).sum();
    }

    public int getTotalPrice() {
        return this.totalPrice;
    }
}
