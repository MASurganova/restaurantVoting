package ru.voting.model;

import java.util.Objects;

public class Dish extends AbstractBaseEntity {

    private String description;
    private int price;
    private Restaurant restaurant;

    public Dish(Integer id, String description, int price, Restaurant restaurant) {
        super(id);
        this.description = description;
        this.price = price;
        this.restaurant = restaurant;
    }

    public Dish(String description, int price) {
        this(null, description, price, null);
    }

    public Dish(int id, String description, int price) {
        this(id, description, price, null);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "description='" + description + '\'' +
                ", price=" + price +
                ", restaurant=" + restaurant +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;

        Dish dish = (Dish) o;

        if (!Objects.equals(id, dish.getId())) return false;
        if (price != dish.price) return false;
        return description != null ? description.equals(dish.description) : dish.description == null;
    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + price;
        return result;
    }
}
