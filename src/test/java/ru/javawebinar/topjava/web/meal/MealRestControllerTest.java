package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealToTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.user;
import static ru.javawebinar.topjava.web.meal.MealRestController.REST_MEAL_URL;

class MealRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MealService mealService;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_MEAL_URL).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MEAL_TO_MATCHER.contentJson(mealsTo));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_MEAL_URL + "/100002").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MEAL_MATCHER.contentJson(meal1));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_MEAL_URL + "/" + meal1.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        MEAL_MATCHER.assertMatch(mealService.getAll(user.getId()), List.of(meal7, meal6, meal5, meal4, meal3, meal2));
    }

    @Test
    void createMeal() throws Exception {
        Meal mealNew = MealTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_MEAL_URL)
                .content(JsonUtil.writeValue(mealNew))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        mealNew.setId(100011);
        MEAL_MATCHER.assertMatch(mealService.get(100011, USER_ID), mealNew);
    }

    @Test
    void update() throws Exception {
        Meal mealUpdated = MealTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_MEAL_URL + "/" + meal1.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(mealUpdated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        MEAL_MATCHER.assertMatch(mealService.get(meal1.getId(), USER_ID), mealUpdated);

    }

    @Test
    void getBetweenMeals() throws Exception {
        perform(MockMvcRequestBuilders.get(
                REST_MEAL_URL + "/filter" + "?start-date=2020-01-01&end-date=2020-01-30&start-time=09:00&end-time=15:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MEAL_TO_MATCHER.contentJson(mealTo2, mealTo1));
    }
}