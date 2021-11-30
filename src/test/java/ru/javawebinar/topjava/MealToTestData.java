package ru.javawebinar.topjava;

import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

public class MealToTestData {
    public static final MatcherFactory.Matcher<MealTo> MEAL_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MealTo.class);
    public static final List<MealTo> mealsTo = MealsUtil.getTos(MealTestData.meals, MealsUtil.DEFAULT_CALORIES_PER_DAY);
}
