package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("user id {}, create {}", userId, meal);
//        checkNew(meal);
        return service.create(userId, meal);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("user id {}, delete {}", userId, id);
        service.delete(userId, id);
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("user id {}, get {}", userId, id);
        return service.get(userId, id);
    }

    public List<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll collection MealTo - user id: {}", userId);
        return service.getAll(userId);
    }

    public List<MealTo> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("get between date: {} and {}, time: {} and {}. User id: {}",
                startDate, endDate, startTime, endTime, userId);
        return service.getBetween(userId, startDate, startTime, endDate, endTime);
    }
}