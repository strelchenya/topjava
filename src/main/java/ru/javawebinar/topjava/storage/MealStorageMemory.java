package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealStorageMemory implements MealStorage {

    private static final AtomicInteger count = new AtomicInteger();

    private final Map<Integer, Meal> mealsStorage = new ConcurrentHashMap<>();

    {
        add(new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        add(new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        add(new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        add(new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        add(new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(count.incrementAndGet());
        return mealsStorage.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        mealsStorage.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        if (meal.getId() > count.get() || meal.getId() < 1) {
            return null;
        }
        return mealsStorage.put(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealsStorage.values());
    }

    @Override
    public Meal get(int id) {
        return mealsStorage.get(id);
    }
}
