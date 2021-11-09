package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void getByMealOnId() {
        Meal mealActual = service.getWithUser(MealTestData.MEAL1_ID);
        MealTestData.MEAL_MATCHER.assertMatch(mealActual, MealTestData.meal1);
    }

    @Test
    public void getNullOnId(){
        Assert.assertNull(service.getWithUser(MealTestData.NOT_FOUND));
    }

    @Test
    public void getNotOwnByMealOnId() {
        Meal mealActual = service.getWithUser(MealTestData.MEAL1_ID);
        Assert.assertNotEquals(mealActual.id(), MealTestData.ADMIN_MEAL_ID);
    }
}
