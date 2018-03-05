package ru.voting.model;

import java.util.List;

public class Restaurant extends AbstractNamedEntity {
    private List<Lunch> lunches;
    private Lunch currentLunch;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, List<Lunch> lunches, Lunch currentLunch) {
        this(id, name);
        this.lunches = lunches;
        this.currentLunch = currentLunch;
    }

    public void addLunch(Lunch lunch) {
        lunches.add(lunch);
    }

    public void removeLunch(Lunch lunch) {
        lunches.remove(lunch);
    }

    public List<Lunch> getLunches() {
        return lunches;
    }

    public void setLunches(List<Lunch> lunches) {
        this.lunches = lunches;
    }

    public Lunch getCurrentLunch() {
        return currentLunch;
    }

    public void setCurrentLunch(Lunch currentLunch) {
        this.currentLunch = currentLunch;
    }
}
