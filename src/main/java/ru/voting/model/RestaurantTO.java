package ru.voting.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RestaurantTO{

    private String name;
    private List<Dish> lunch;
    private AtomicInteger voters;

    public RestaurantTO(Restaurant restaurant) {
        this.name = String.valueOf(restaurant.getName());
        this.lunch = new ArrayList<>(restaurant.getLunch());
        voters = new AtomicInteger(0);
    }

    public int addVoter() {
        return voters.incrementAndGet();
    }

    public int removeVoter() {
        return voters.decrementAndGet();
    }

    public int getVoters() {
        return voters.get();
    }

    public String getName() {
        return name;
    }

    public List<Dish> getLunch() {
        return lunch;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalPrice() {
        return lunch.stream().mapToInt(Dish::getPrice).sum();
    }

    public void setLunch(List<Dish> lunch) {
        this.lunch = lunch;
    }

    public void setVoters(AtomicInteger voters) {
        this.voters = voters;
    }
}
