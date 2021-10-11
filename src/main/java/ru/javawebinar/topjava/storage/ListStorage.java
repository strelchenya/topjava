package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ListStorage implements Storage {

    private final List<Meal> mealsStorage = new ArrayList<>();

    {
        mealsStorage.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealsStorage.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealsStorage.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealsStorage.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealsStorage.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealsStorage.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealsStorage.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    protected Integer getSearchKey(String id) {
        for (int i = 0; i < mealsStorage.size(); i++) {
            if (mealsStorage.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void add(Meal meal) {
        mealsStorage.add(meal);
    }

    @Override
    public void delete(String id) {
        if (getSearchKey(id) != -1) {
            mealsStorage.remove(getSearchKey(id).intValue());
        } else {
            throw new NullPointerException("Шеф, всё пропало! Нет такого id");
        }
    }

    @Override
    public void update(Meal meal) {
        mealsStorage.set(getSearchKey(meal.getId()), meal);
    }

    @Override
    public List<MealTo> getAll() {
        return MealsUtil.filteredByStreams(mealsStorage, MealsUtil.START_TIME, MealsUtil.END_TIME, MealsUtil.CALORIES_PER_DAY);
    }

    @Override
    public Meal get(String id) {
        return mealsStorage.get(getSearchKey(id));
    }
}
