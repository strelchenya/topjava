package ru.javawebinar.topjava.repository.jdbc;

import java.time.LocalDateTime;


public abstract class ConvertLocalDateTime {
    public abstract <T> T convert(LocalDateTime localDateTime);
}
