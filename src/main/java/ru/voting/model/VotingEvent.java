package ru.voting.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "history", uniqueConstraints = {@UniqueConstraint(columnNames = "event_date", name = " history_unique_date_idx")})
@Access(AccessType.FIELD)
public class VotingEvent {

    @Id
    @Column(name = "event_date", columnDefinition = "timestamp default now()", unique = true)
    @NotNull
    private LocalDate date;

    @Column(name = "restaurant_name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String restaurantName;

    public VotingEvent() {
    }

    public VotingEvent(LocalDate date, String restaurantName) {
        this.date = date;
        this.restaurantName = restaurantName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VotingEvent that = (VotingEvent) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(restaurantName, that.restaurantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, restaurantName);
    }

    @Override
    public String toString() {
        return "VotingEvent{" +
                "date=" + date +
                ", restaurantName='" + restaurantName + '\'' +
                '}';
    }
}
