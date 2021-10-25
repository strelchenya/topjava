package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final LocalDateTime startDateTime = LocalDateTime.of(2020, 1, 30, 9, 0);
    public static final LocalDateTime endDateTime = LocalDateTime.of(2020, 1, 30, 23, 0);
    public static final Meal meal1 = new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal meal4 =
            new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal meal6 = new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal meal7 = new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "newDescription", 123);
    }

    public static Meal getUpdate() {
        Meal mealUpdate = new Meal(meal1);
        mealUpdate.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        mealUpdate.setDescription("Afternoon tea");
        mealUpdate.setCalories(333);
        return mealUpdate;
    }

    public static List<Meal> getBetween(){
        List<Meal> actualMeals = Arrays.asList(meal1, meal2, meal3, meal4, meal5, meal6, meal7);
        return Optional.of(actualMeals).orElse(Collections.emptyList()).stream()
                .filter(meal ->
                        Util.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }

}
