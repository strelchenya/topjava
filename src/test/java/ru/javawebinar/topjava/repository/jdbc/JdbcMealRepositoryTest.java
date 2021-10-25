package ru.javawebinar.topjava.repository.jdbc;

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
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
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
public class JdbcMealRepositoryTest {

    @Autowired
    private MealRepository mealRepository;

    static {
        SLF4JBridgeHandler.install();
    }

    @Test
    public void save() {
        Meal mealActual = new Meal(MealTestData.getNew());
        Meal mealExpected = mealRepository.save(mealActual, USER_ID);
        MealTestData.assertMatch(mealActual, mealExpected);
    }

    @Test
    public void delete() {
        mealRepository.delete(meal1.getId(), USER_ID);
        Assert.assertNull(mealRepository.get(meal1.getId(), USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertFalse(mealRepository.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void get() {
        Meal meal = mealRepository.get(meal1.getId(), USER_ID);
        MealTestData.assertMatch(meal, meal1);
    }

    @Test
    public void getNotFound() {
        Assert.assertNotNull(mealRepository.get(meal1.getId(), USER_ID));
    }

    @Test
    public void getAll() {
        List<Meal> expectedMeals = mealRepository.getAll(USER_ID);
        List<Meal> actualMeals = Arrays.asList(meal1, meal2, meal3, meal4, meal5, meal6, meal7);
        List<Meal> actualSortedMeals = actualMeals.stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        assertMatch(actualSortedMeals, expectedMeals);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> expectedMeals = mealRepository.getBetweenHalfOpen(
                startDateTime, endDateTime, USER_ID);
        List<Meal> actualMeals = MealTestData.getBetween();
        assertMatch(actualMeals, expectedMeals);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class,
                () -> mealRepository.save(new Meal(null,
                        LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Afternoon tea", 2345), USER_ID));
    }
}