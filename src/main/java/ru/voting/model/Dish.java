package ru.voting.model;

public class Dish extends AbstractBaseEntity {

    private String desctription;
    private int price;

    public Dish(Integer id, String description, int price) {
        super(id);
        this.desctription = description;
        this.price = price;
    }

    public String getDesctription() {
        return desctription;
    }

    public void setDesctription(String desctription) {
        this.desctription = desctription;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
