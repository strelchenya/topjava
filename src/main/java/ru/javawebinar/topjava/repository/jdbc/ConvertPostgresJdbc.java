package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.Profiles;

import java.time.LocalDateTime;

@Component
@Profile(Profiles.POSTGRES_DB)
public class ConvertPostgresJdbc extends ConvertLocalDateTime {
    @Override
    public LocalDateTime convert(LocalDateTime localDateTime) {
        return localDateTime;
    }
}