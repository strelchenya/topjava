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
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
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
        Meal meal = service.get(meal1.getId(), USER_ID);
        assertMatch(meal, meal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(meal1.getId(), USER_ID);
        Assert.assertThrows(NotFoundException.class, () -> service.get(meal1.getId(), USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> expectedMeals = service.getBetweenInclusive(
                startDateTime.toLocalDate(), endDateTime.toLocalDate(), USER_ID);
        List<Meal> actualMeals = MealTestData.getBetween();
        assertMatch(actualMeals, expectedMeals);
    }

    @Test
    public void getAll() {
        List<Meal> expectedMeals = service.getAll(USER_ID);
        List<Meal> actualMeals = Arrays.asList(meal1, meal2, meal3, meal4, meal5, meal6, meal7);
        List<Meal> actualSortedMeals = actualMeals.stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        assertMatch(actualSortedMeals, expectedMeals);
    }

    @Test
    public void update() {
        Meal mealUpdated = getUpdate();
        service.update(mealUpdated, USER_ID);
        assertMatch(service.get(meal1.getId(), USER_ID),
                getUpdate());
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
                        LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Afternoon tea", 2345), USER_ID));
    }
}