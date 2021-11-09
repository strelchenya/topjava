package ru.javawebinar.topjava.repository.jdbc;

import java.time.LocalDateTime;

public abstract class JdbcDriver {
    public abstract <T> T convert(LocalDateTime localDateTime);

    public <T> T convertByProfile(LocalDateTime localDateTime) {
        return convert(localDateTime);
    }
}
