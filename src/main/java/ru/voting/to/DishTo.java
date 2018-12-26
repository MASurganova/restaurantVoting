package ru.voting.to;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DishTo extends BaseTo {

    @NotBlank
    private String description;

    @Range(min = 10, max = 5000)
    @NotNull
    private int price;

    public DishTo() {
    }

    public DishTo(Integer id, String description, int price) {
        super(id);
        this.description = description;
        this.price = price;
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
}
