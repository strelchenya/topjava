package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;

public class ListStorage implements Storage {

    private List<Meal> meals = new ArrayList<>(MealsUtil.MEALS);

    protected Integer getSearchKey(String id) {
        for (int i = 0; i < meals.size(); i++) {
            if (meals.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void add(Meal meal) {
        meals.add(meal);
    }

    @Override
    public void delete(String id) {
        if (getSearchKey(id) != -1) {
            meals.remove(getSearchKey(id));
        }
    }

    @Override
    public void update(Meal meal) {
        if (getSearchKey(meal.getId()) != -1) {
            meals.set(getSearchKey(meal.getId()), meal);
        }
    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }

    @Override
    public Meal get(String id) {
        if (getSearchKey(id) != -1) {
            return meals.get(getSearchKey(id));
        }
        return null;
    }
}
