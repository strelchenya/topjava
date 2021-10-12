package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {

    Meal add(Meal meal);

    void delete(Integer id);

    Meal update(Meal meal);

    List<Meal> getAll();

    Meal get(Integer id);
}
