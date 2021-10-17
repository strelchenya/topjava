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
    private final Map<Integer, List<Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(1, meal));
        save(2, new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        save(2, new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        save(2, new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Ужин", 500));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        List<Meal> newMeals = new ArrayList<>();

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            //+ -> -- add new list
            if (!repository.containsKey(userId)) {
                newMeals.add(meal);
                //+ -> -+ add
            } else {
                newMeals = repository.get(userId);
                newMeals.add(meal);
            }
            repository.put(userId, newMeals);
        } else {
            //- -> ++ update
            if (repository.containsKey(userId)) {
                repository.computeIfPresent(userId, (id, oldMeal) -> repository.get(userId).stream()
                        .map(m -> Objects.equals(m.getId(), meal.getId()) ? meal : m)
                        .collect(Collectors.toList()));
                //- -> -+
            } else {
//                throw new NotFoundException(meal + " does not apply to user id: " + userId + "!");
                return null;
            }
        }
        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        return repository.get(userId).removeIf(meal -> meal.getId() == id);
    }

    @Override
    public Meal get(int userId, int id) {
        return repository.get(userId).stream()
                .filter(meal -> meal.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return Optional.ofNullable(repository.get(userId)).orElse(Collections.emptyList()).stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetween(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

