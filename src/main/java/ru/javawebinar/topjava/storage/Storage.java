package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface Storage {

    void add(Meal meal);

    void delete(String id);

    void update(Meal meal);

    List<MealTo> getAll();

    Meal get(String id);

//    void clear();
    
//    int size();
}
