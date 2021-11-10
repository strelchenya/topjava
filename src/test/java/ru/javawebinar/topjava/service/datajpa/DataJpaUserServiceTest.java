package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getById() {
        User userActual = userService.getWithMeal(UserTestData.USER_ID);
        UserTestData.USER_MATCHER.assertMatch(userActual, UserTestData.user);
        List<Meal> mealsActual = userActual.getMeals();
        List<Meal> mealsExpected = List.of(MealTestData.meal7, MealTestData.meal6,MealTestData.meal5,
                MealTestData.meal4, MealTestData.meal3, MealTestData.meal2, MealTestData.meal1);
        MealTestData.MEAL_MATCHER.assertMatch(mealsActual,mealsExpected);
    }

    @Test
    public void getNotFoundById() {
//        Assert.assertNull(userService.getWithMeal(UserTestData.USER_ID_NOT_FOUND_MEALS));
        Assert.assertThrows(NotFoundException.class, ()-> userService.getWithMeal(UserTestData.USER_ID_NOT_FOUND_MEALS));

    }
}
