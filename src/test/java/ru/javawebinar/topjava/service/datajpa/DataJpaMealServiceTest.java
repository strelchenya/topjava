package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void getByMealOnId() {
        Meal mealActual = mealService.getWithUser(MealTestData.MEAL1_ID);
        MealTestData.MEAL_MATCHER.assertMatch(mealActual, MealTestData.meal1);
    }

    @Test
    public void getNullOnId(){
        Assert.assertThrows(NotFoundException.class, ()-> mealService.getWithUser(MealTestData.MEAL_ID_NOT_FOUND_MEALS));
    }

    @Test
    public void getNotOwnByMealOnId() {
        Meal mealActual = mealService.getWithUser(MealTestData.MEAL1_ID);
        Assert.assertNotEquals(mealActual.id(), MealTestData.ADMIN_MEAL_ID);
    }
}
