package ru.voting.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(mappedBy = "restaurant")
    private List<Dish> lunch;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default false")
    @NotNull
    private boolean enabled;

    @Column(name = "price", columnDefinition = "int default 0")
    private AtomicInteger voters;

    private int totalPrice;

    public Restaurant() {
        this.lunch = new ArrayList<>();
        this.voters = new AtomicInteger(0);
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
        this.lunch = new ArrayList<>();
        this.voters = new AtomicInteger(0);
    }

    public Restaurant(Integer id, String name, List<Dish> lunch) {
        this(id, name);
        this.lunch = lunch;
        setTotalPrice();
    }

    public Restaurant(Integer id, String name, boolean enabled, int voters) {
        this(id, name);
        setEnabled(enabled);
        setVoters(voters);
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getLunch());
        setVoters(restaurant.getVoters());
        setEnabled(restaurant.enabled);
    }

    public void addDish(Dish dish) {
        this.lunch.removeIf(d -> Objects.equals(d.getId(), dish.getId()));
        this.lunch.add(dish);
        setTotalPrice();
    }

    public void removeDish(Dish dish) {
        this.lunch.remove(dish);
        setTotalPrice();
    }

    public void setEnabled(boolean enabeled) {
        this.enabled = enabeled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getVoters() {
        return voters == null ? 0 : voters.get();
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
        setTotalPrice();
    }

    private void setTotalPrice() {
        if (this.lunch != null && this.lunch.size() != 0)
            totalPrice = this.lunch.stream().mapToInt(Dish::getPrice).sum();
    }

    public int getTotalPrice() {
        setTotalPrice();
        return this.totalPrice;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                " totalPrice=" + getTotalPrice() +
                ", enabled=" + enabled +
                ", voters=" + getVoters() +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
