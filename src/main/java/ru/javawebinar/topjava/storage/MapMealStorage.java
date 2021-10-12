package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapMealStorage implements MealStorage {

    private static final AtomicInteger count = new AtomicInteger(6);

    private final ConcurrentHashMap<Integer, Meal> mealsStorage = new ConcurrentHashMap<>();

    {
        mealsStorage.put(0, new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealsStorage.put(1, new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealsStorage.put(2, new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealsStorage.put(3, new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealsStorage.put(4, new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealsStorage.put(5, new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealsStorage.put(6, new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(count.incrementAndGet());
        return mealsStorage.put(meal.getId(), meal);
    }

    @Override
    public void delete(Integer id) {
        mealsStorage.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        return mealsStorage.put(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealsStorage.values());
    }

    @Override
    public Meal get(Integer id) {
        return mealsStorage.get(id);
    }
}
