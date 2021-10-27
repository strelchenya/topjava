package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-app-jdbc.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID_1, USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getFoodFromAnotherUser() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_1, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID_1, USER_ID);
        Assert.assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_1, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteFoodFromAnotherUser() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID_1, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> actualMeals = service.getBetweenInclusive(START_DATE, END_DATE, USER_ID);
        List<Meal> expectedMeals =
                Arrays.asList(userMeal3, userMeal2, userMeal1);
        assertMatch(actualMeals, expectedMeals);
    }

    @Test
    public void getBetweenInclusiveNullDates() {
        List<Meal> actualMeals = service.getBetweenInclusive(null, null, USER_ID);
        List<Meal> expectedMeals =
                Arrays.asList(userMeal7, userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
        assertMatch(actualMeals, expectedMeals);
    }

    @Test
    public void getAll() {
        List<Meal> actualMeals = service.getAll(USER_ID);
        List<Meal> expectedMeals =
                Arrays.asList(userMeal7, userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
        assertMatch(actualMeals, expectedMeals);
    }

    @Test
    public void update() {
        Meal mealUpdated = getUpdated();
        service.update(mealUpdated, USER_ID);
        assertMatch(service.get(MEAL_ID_1, USER_ID), getUpdated());
    }

    @Test
    public void updateFoodFromAnotherUser() {
        assertThrows(NotFoundException.class, () -> service.update(userMeal1, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal mealCreated = service.create(getNew(), USER_ID);
        Integer newId = mealCreated.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(mealCreated, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class,
                () -> service.create(new Meal(null,
                        userMeal1.getDateTime(), "Afternoon tea", 2345), USER_ID));
    }
}