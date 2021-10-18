package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(1, meal));
        save(2, new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        save(2, new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        save(2, new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Ужин", 500));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> userMeals;
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
            return userMeals.put(meal.getId(), meal);
        } else if (get(userId, meal.getId()) == null) {
            return null;
        }
        return meal;
    }

    @Override
    public Meal update(int userId, int id, Meal meal) {
        if (get(userId, id) == null) {
            return null;
        }
        Map<Integer, Meal> userMeals = repository.computeIfPresent(userId, (key, value) -> value);
        return Objects.requireNonNull(userMeals).put(id, meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        return Optional.of(repository.get(userId).get(id)).orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return new ArrayList<>(Optional.of(repository.get(userId).values()).orElse(Collections.emptyList()));
    }

    @Override
    public List<Meal> getBetween(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime))
                .collect(Collectors.toList());
    }
}

