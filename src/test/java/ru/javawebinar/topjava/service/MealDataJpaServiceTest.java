package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

@ActiveProfiles(Profiles.DATAJPA)
public class MealDataJpaServiceTest extends MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void getByMealAndUser() {
        Meal mealActual = service.get(MealTestData.MEAL1_ID, UserTestData.USER_ID);
        MealTestData.MEAL_MATCHER.assertMatch(mealActual, MealTestData.meal1);
        UserTestData.USER_MATCHER.assertMatch(mealActual.getUser(), UserTestData.user);
    }
}
