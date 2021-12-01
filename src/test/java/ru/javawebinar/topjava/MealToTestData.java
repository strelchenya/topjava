package ru.javawebinar.topjava;

import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

public class MealToTestData {
    public static final MatcherFactory.Matcher<MealTo> MEAL_TO_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(MealTo.class);

    public static final List<MealTo> mealsTo =
            MealsUtil.getTos(MealTestData.meals, UserTestData.user.getCaloriesPerDay());

    public static final List<MealTo> getMealsToBetween =
            MealsUtil.getTos(List.of(meal2,meal1),UserTestData.user.getCaloriesPerDay());

    public static final List<MealTo> getMealsToBetweenWithNullAndIsEmptyValues =
            MealsUtil.getTos(List.of(meal6,meal5),UserTestData.user.getCaloriesPerDay());
}
