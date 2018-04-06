package ru.voting.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant r WHERE r.id=:id"),
        @NamedQuery(name = Restaurant.BY_NAME, query = "SELECT r FROM Restaurant r WHERE r.name=?1"),
        @NamedQuery(name = Restaurant.ALL_WITH_LUNCH_SORTED, query = "SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.lunch ORDER BY r.name"),
        @NamedQuery(name = Restaurant.ALL_ENABLED_SORTED, query = "SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.lunch WHERE r.enabled=true ORDER BY r.name"),
        @NamedQuery(name = Restaurant.BY_ID_WITH_LUNCH, query = "SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.lunch WHERE r.id=?1")
})
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    public static final String DELETE = "Restaurant.delete";
    public static final String BY_NAME = "Restaurant.getByEmail";
    public static final String ALL_WITH_LUNCH_SORTED = "Restaurant.getAllSorted";
    public static final String ALL_ENABLED_SORTED = "Restaurant.getAllEnabled";
    public static final String BY_ID_WITH_LUNCH = "Restaurant.getByIdWithLunch";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("id")
    private List<Dish> lunch;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default false")
    @NotNull
    private boolean enabled;

    @Column(name = "voters", columnDefinition = "int default 0")
    private int voters;

    public Restaurant() {
        this.lunch = new ArrayList<>();
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
        this.lunch = new ArrayList<>();
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
        return voters;
    }

    public int addVoter() {
        return voters++;
    }

    public int removeVoter() {
        return voters++;
    }

    public void setVoters(int voters) {
        this.voters = voters;
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
