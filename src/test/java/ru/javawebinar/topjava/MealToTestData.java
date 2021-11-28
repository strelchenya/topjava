package ru.javawebinar.topjava;

import ru.javawebinar.topjava.to.MealTo;

import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealToTestData {
    public static final MatcherFactory.Matcher<MealTo> MEAL_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MealTo.class, "");

    //    public static final int NOT_FOUND = 10;
    public static final int MEAL_TO_1_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_TO_ID = START_SEQ + 9;

    public static final MealTo mealTo1 = new MealTo(MEAL_TO_1_ID, of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, false);
    public static final MealTo mealTo2 = new MealTo(MEAL_TO_1_ID + 1, of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000, false);
    public static final MealTo mealTo3 = new MealTo(MEAL_TO_1_ID + 2, of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500, false);
    public static final MealTo mealTo4 = new MealTo(MEAL_TO_1_ID + 3, of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, true);
    public static final MealTo mealTo5 = new MealTo(MEAL_TO_1_ID + 4, of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 500, true);
    public static final MealTo mealTo6 = new MealTo(MEAL_TO_1_ID + 5, of(2020, Month.JANUARY, 31, 13, 0), "Обед", 1000, true);
    public static final MealTo mealTo7 = new MealTo(MEAL_TO_1_ID + 6, of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 510, true);
    public static final MealTo adminMealTo1 = new MealTo(ADMIN_MEAL_TO_ID, of(2020, Month.JANUARY, 31, 14, 0), "Админ ланч", 510, false);
    public static final MealTo adminMealTo2 = new MealTo(ADMIN_MEAL_TO_ID + 1, of(2020, Month.JANUARY, 31, 21, 0), "Админ ужин", 1500, false);

    public static final List<MealTo> mealsTo = List.of(mealTo7, mealTo6, mealTo5, mealTo4, mealTo3, mealTo2, mealTo1);
}
