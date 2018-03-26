package ru.voting.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Restaurant extends AbstractNamedEntity {

    private List<Dish> lunch;
    private int totalPrice;

    private boolean enabled = false;

    private AtomicInteger voters;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
        this.lunch = new ArrayList<>();
        this.voters = new AtomicInteger(0);
    }

    public Restaurant(Integer id, String name, List<Dish> lunch) {
        this(id, name);
        this.lunch = lunch;
        totalPrice = lunch.stream().mapToInt(Dish::getPrice).sum();
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getLunch());
        this.voters = new AtomicInteger(restaurant.getVoters());
        setEnabled(restaurant.enabled);
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

    public void setEnabled(boolean enabeled) {
        this.enabled = enabeled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getVoters() {
        return voters.get();
    }

    public int addVoter() {
        return voters.incrementAndGet();
    }

    public int removeVoter() {
        return voters.decrementAndGet();
    }

    public void setVoters(int voters) {
        this.voters.set(voters);
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
                "lunch=" + lunch.size() +
                ", totalPrice=" + totalPrice +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant)) return false;

        Restaurant that = (Restaurant) o;
        if (id == null ? that.getId() != null : !Objects.equals(id, that.id)) return false;
        if (name == null ? that.name != null : !name.equals(that.name)) return false;
        return (lunch != null || that.lunch == null) && that.lunch != null && lunch.size() == that.lunch.size() && lunch.containsAll(that.lunch);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + totalPrice;
        result = 31 * result + name.hashCode();
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + (voters != null ? voters.hashCode() : 0);
        return result;
    }
}
