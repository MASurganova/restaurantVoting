package ru.voting.model;

import java.util.List;

public class Restaurant extends AbstractNamedEntity {

    private List<Dish> lunch;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, List<Dish> lunch) {
        this(id, name);
        this.lunch = lunch;
    }

    public void addDish(Dish dish) {
        this.lunch.add(dish);
    }

    public void removeDish(Dish dish) {
        this.lunch.remove(dish);
    }

    public List<Dish> getLunch() {
        return lunch;
    }

    public void setLunch(List<Dish> lunch) {
        this.lunch = lunch;
    }
}
