package ru.voting.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NamedQueries({
        @NamedQuery(name = Dish.DELETE, query = "DELETE FROM Dish d WHERE d.id=:id"),
        @NamedQuery(name = Dish.ALL_SORTED, query = "SELECT d FROM Dish d ORDER BY d.id")
})
@Entity
@Table(name = "dishes")
public class Dish extends AbstractBaseEntity {

    public static final String DELETE = "Dish.delete";
    public static final String ALL_SORTED = "Dish.getAllSorted";

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 10000)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Integer id, String description, int price, Restaurant restaurant) {
        super(id);
        this.description = description;
        this.price = price;
        this.restaurant = restaurant;
    }

    public Dish(String description, int price, Restaurant restaurant) {
        this(null, description, price, restaurant);
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
                ", id=" + id +
                '}';
    }


}
