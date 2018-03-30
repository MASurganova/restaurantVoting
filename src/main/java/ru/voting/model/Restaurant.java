package ru.voting.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@NamedQueries({
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant r WHERE r.id=:id"),
        @NamedQuery(name = Restaurant.BY_NAME, query = "SELECT r FROM Restaurant r WHERE r.name=?1"),
        @NamedQuery(name = Restaurant.ALL_SORTED, query = "SELECT r FROM Restaurant r ORDER BY r.name"),
        @NamedQuery(name = Restaurant.ALL_ENABLED_SORTED, query = "SELECT r FROM Restaurant r WHERE r.enabled=true ORDER BY r.name"),
})
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    public static final String DELETE = "Restaurant.delete";
    public static final String BY_NAME = "Restaurant.getByEmail";
    public static final String ALL_SORTED = "Restaurant.getAllSorted";
    public static final String ALL_ENABLED_SORTED = "Restaurant.getAllEnabled";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("id")
    private List<Dish> lunch;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default false")
    @NotNull
    private boolean enabled;

    @Column(name = "voters", columnDefinition = "int default 0")
    private AtomicInteger voters;

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
    }

    public void removeDish(Dish dish) {
        this.lunch.remove(dish);
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
    }

    public int getTotalPrice() {
        if (lunch == null || lunch.size() == 0) return 0;
        return lunch.stream().mapToInt(Dish::getPrice).sum();
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                " enabled=" + enabled +
                ", voters=" + getVoters() +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

}
