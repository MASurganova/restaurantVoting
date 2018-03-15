package ru.voting.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Restaurant extends AbstractNamedEntity {

    private List<Dish> lunch;
    private int totalPrice;

    public Restaurant(Integer id, String name) {
        super(id, name);
        this.lunch = new ArrayList<>();
    }

    public Restaurant(Integer id, String name, List<Dish> lunch) {
        this(id, name);
        this.lunch = lunch;
        totalPrice = lunch.stream().mapToInt(Dish::getPrice).sum();
    }

    public void addDish(Dish dish) {
        this.lunch.removeIf(d -> Objects.equals(d.getId(), dish.getId()));
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

    @Override
    public String toString() {
        return "Restaurant{" +
                "lunch=" + lunch +
                ", totalPrice=" + totalPrice +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
