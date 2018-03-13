package ru.voting.model;

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
}
