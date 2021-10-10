package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public class Meal {

    private String id;

    private LocalDateTime dateTime;

    private String description;

    private int calories;


    public Meal(LocalDateTime dateTime, String description, int calories) {
        Objects.requireNonNull(description, "Description must not be null");
        this.id = UUID.randomUUID().toString();
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return calories == meal.calories && Objects.equals(id, meal.id) && Objects.equals(dateTime, meal.dateTime) && Objects.equals(description, meal.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, description, calories);
    }
}
