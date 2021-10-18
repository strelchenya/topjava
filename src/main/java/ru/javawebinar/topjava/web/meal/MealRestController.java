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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        int userId = authUserId();
        log.error("user id {}, create {}", userId, meal);
//        checkNew(meal);
        return service.create(userId, meal);
    }

    public void update(Meal meal, int id) {
        log.error("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        int userId = authUserId();
        service.update(userId, meal);
    }

    public void delete(int id) {
        int userId = authUserId();
        log.error("user id {}, delete {}", userId, id);
        service.delete(userId, id);
    }

    public Meal get(int id) {
        int userId = authUserId();
        log.error("user id {}, get {}", userId, id);
        return service.get(userId, id);
    }

    public List<MealTo> getAll() {
        int userId = authUserId();
        log.error("getAll collection MealTo - user id: {}", userId);
        return service.getAll(userId).stream()
                .sorted(Comparator.comparing(MealTo::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    public List<MealTo> getBetween(String startDate, String startTime, String endDate, String endTime) {
        int userId = authUserId();
        LocalDate startLocalDate = DateTimeUtil.parseLocalDateOrDefault(startDate, DateTimeUtil.MIN_DATE);
        LocalDate endLocalDate = DateTimeUtil.parseLocalDateOrDefault(endDate, DateTimeUtil.MAX_DATE);
        LocalTime startLocalTime = DateTimeUtil.parseLocalTimeOrDefault(startTime, LocalTime.MIN);
        LocalTime endLocalTime = DateTimeUtil.parseLocalTimeOrDefault(endTime, LocalTime.MAX);
        log.error("get between date: {} and {}, time: {} and {}. User id: {}",
                startDate, endDate, startTime, endTime, userId);
        return service.getBetween(userId, startLocalDate, startLocalTime,
                        endLocalDate, endLocalTime, authUserCaloriesPerDay()).stream()
                .sorted(Comparator.comparing(MealTo::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}