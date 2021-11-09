package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

@ActiveProfiles(Profiles.DATAJPA)
public class UserDataJpaServiceTest extends UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getByUserAndMeals() {
        User userActual = userService.get(UserTestData.USER_ID);
        List<Meal> mealsActual = userActual.getMeals();
        List<Meal> mealsExpected = List.of(MealTestData.meal1,
                MealTestData.meal2, MealTestData.meal3, MealTestData.meal4, MealTestData.meal5, MealTestData.meal6,
                MealTestData.meal7);
        UserTestData.USER_MATCHER.assertMatch(userActual, UserTestData.user);
        MealTestData.MEAL_MATCHER.assertMatch(mealsActual, mealsExpected);
    }
}
