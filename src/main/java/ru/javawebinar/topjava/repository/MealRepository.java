package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {
    // null if updated meal do not belong to userId
    // null, если обновленное блюдо не принадлежит userId
    Meal save(int userId, Meal meal);

    // false if meal do not belong to userId
//    false, если еда не принадлежит userId
    boolean delete(int userId, int id);

    // null if meal do not belong to userId
    // null, если еда не принадлежит userId
    Meal get(int userId, int id);

    // ORDERED dateTime desc
    List<Meal> getAll(int userId);

    List<Meal> getBetween(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
