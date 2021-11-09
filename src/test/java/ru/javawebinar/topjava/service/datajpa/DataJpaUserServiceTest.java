package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getById() {
        User userActual = userService.getWithMeal(UserTestData.USER_ID);
        UserTestData.USER_MATCHER.assertMatch(userActual, UserTestData.user);
    }

    @Test
    public void getNotFoundById() {
        Assert.assertNull(userService.getWithMeal(UserTestData.USER_ID_NOT_FOUND_MEALS));
    }
}
