package model;

import java.util.List;

public class Restaurant {
    private String name;
    private List<Lunch> lunches;
    private Lunch currentLunch;

    public Restaurant(String name) {
        this.name = name;
    }

    public Restaurant(String name, List<Lunch> lunches) {
        this.name = name;
        this.lunches = lunches;
    }

    public void addLunch(Lunch lunch) {
        lunches.add(lunch);
    }

    public void removeLunch(Lunch lunch) {
        lunches.remove(lunch);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
