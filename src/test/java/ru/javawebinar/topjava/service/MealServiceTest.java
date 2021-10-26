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

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-app-2.xml",
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
        Meal meal = service.get(MEAL_ID_1, MEAL_USER_ID);
        assertMatch(meal, meal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_NOT_FOUND, MEAL_USER_ID));
    }

    @Test
    public void getFoodFromAnotherUser() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_1, MEAL_ADMIN_ID));
//        Assert.assertNull(service.get(MEAL_ID_1, MEAL_ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID_1, MEAL_USER_ID);
        Assert.assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_1, MEAL_USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_NOT_FOUND, MEAL_USER_ID));
    }

    @Test
    public void deleteFoodFromAnotherUser() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID_1, MEAL_ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> actualMeals = service.getBetweenInclusive(
                START_DATE_TIME, END_DATE_TIME, MEAL_USER_ID);
        List<Meal> expectedMeals = Arrays.asList(meal7, meal6, meal5, meal4, meal3, meal2, meal1);
        assertMatch(actualMeals, expectedMeals);
    }

    @Test
    public void getBetweenInclusiveNullDates() {
        List<Meal> actualMeals = service.getBetweenInclusive(
                null, null, MEAL_USER_ID);
        List<Meal> expectedMeals = Arrays.asList(meal7, meal6, meal5, meal4, meal3, meal2, meal1);
        assertMatch(actualMeals, expectedMeals);
    }

    @Test
    public void getAll() {
        List<Meal> actualMeals = service.getAll(MEAL_USER_ID);
        List<Meal> expectedMeals = Arrays.asList(meal7, meal6, meal5, meal4, meal3, meal2, meal1);
        assertMatch(actualMeals, expectedMeals);
    }

    @Test
    public void update() {
        Meal mealUpdated = getUpdated();
        service.update(mealUpdated, MEAL_USER_ID);
        assertMatch(service.get(MEAL_ID_1, MEAL_USER_ID),
                getUpdated());
    }

    @Test
    public void updateFoodFromAnotherUser() {
        assertThrows(NotFoundException.class, () -> service.update(meal1, MEAL_ADMIN_ID));
    }

    @Test
    public void create() {
        Meal mealCreated = service.create(getNew(), MEAL_USER_ID);
        Integer newId = mealCreated.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(mealCreated, newMeal);
        assertMatch(service.get(newId, MEAL_USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class,
                () -> service.create(new Meal(null,
                        meal1.getDateTime(), "Afternoon tea", 2345), MEAL_USER_ID));
    }
}