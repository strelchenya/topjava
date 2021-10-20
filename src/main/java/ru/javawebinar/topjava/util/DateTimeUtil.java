package ru.javawebinar.topjava.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static final LocalDate MAX_DATE = LocalDate.of(2599, 1, 1);
    public static final LocalDate MIN_DATE = LocalDate.of(0, 1, 1);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpenDateTimeOrTime(
            T lt, T starDateTime, T endDateTime) {
        return lt.compareTo(starDateTime) >= 0 && lt.compareTo(endDateTime) < 0;
    }

    public static LocalDate parseLocalDateOrDefault(String dateString, LocalDate localDate) {
        return StringUtils.isEmpty(dateString) ? localDate : LocalDate.parse(dateString);
    }

    public static LocalTime parseLocalTimeOrDefault(String timeString, LocalTime localTime) {
        return StringUtils.isEmpty(timeString) ? localTime : LocalTime.parse(timeString);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

