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
        if (repository.get(userId) == null) {
            repository.put(userId, new ConcurrentHashMap<>());
        }

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        return repository.get(userId).put(meal.getId(), meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        if (repository.get(userId) == null) {
            return false;
        }
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        if (repository.get(userId) == null /*|| repository.get(userId).get(id) == null*/) {
            return null;
        }
        return repository.get(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        if (repository.get(userId) == null) {
            return Collections.emptyList();
        }
        return Optional.of(repository.get(userId).values()).orElse(Collections.emptyList()).stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetween(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (repository.get(userId) == null) {
            return Collections.emptyList();
        }
        return Optional.of(repository.get(userId).values()).orElse(Collections.emptyList()).stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

