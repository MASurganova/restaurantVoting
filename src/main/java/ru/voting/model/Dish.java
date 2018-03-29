package ru.voting.model;

import java.util.Objects;

public class Dish extends AbstractBaseEntity {

    private String description;
    private int price;
    private Integer restaurantId;

    public Dish() {
    }

    public Dish(Integer id, String description, int price, Integer restaurantId) {
        super(id);
        this.description = description;
        this.price = price;
        this.restaurantId = restaurantId;
    }

    public Dish(String description, int price, int restaurantId) {
        this(null, description, price, restaurantId);
    }

    public Dish(Integer id, String description, int price) {
        this(id, description, price, null);
    }

    public Dish(String description, int price) {
        this(null, description, price, null);
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

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "description='" + description + '\'' +
                ", price=" + price +
                ", restaurantId=" + restaurantId +
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
