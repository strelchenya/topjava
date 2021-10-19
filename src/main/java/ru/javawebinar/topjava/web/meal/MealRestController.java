package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        int userId = authUserId();
        log.info("user id {}, create {}", userId, meal);
        checkNew(meal);
        return service.create(userId, meal);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        int userId = authUserId();
        service.update(userId, meal);
    }

    public void delete(int id) {
        int userId = authUserId();
        log.info("user id {}, delete {}", userId, id);
        service.delete(userId, id);
    }

    public Meal get(int id) {
        int userId = authUserId();
        log.info("user id {}, get {}", userId, id);
        return service.get(userId, id);
    }

    public List<MealTo> getAll() {
        int userId = authUserId();
        log.info("getAll collection MealTo - user id: {}", userId);
        return service.getAll(userId);
    }

    public List<MealTo> getBetween(String startDate, String startTime, String endDate, String endTime) {
        int userId = authUserId();
        LocalDate startLocalDate = DateTimeUtil.parseLocalDateOrDefault(startDate, DateTimeUtil.MIN_DATE);
        LocalDate endLocalDate = DateTimeUtil.parseLocalDateOrDefault(endDate, DateTimeUtil.MAX_DATE);
        LocalTime startLocalTime = DateTimeUtil.parseLocalTimeOrDefault(startTime, LocalTime.MIN);
        LocalTime endLocalTime = DateTimeUtil.parseLocalTimeOrDefault(endTime, LocalTime.MAX);
        log.info("get between date: {} and {}, time: {} and {}. User id: {}",
                startDate, endDate, startTime, endTime, userId);
        return service.getBetween(userId, startLocalDate, startLocalTime,
                        endLocalDate, endLocalTime, authUserCaloriesPerDay());
    }
}