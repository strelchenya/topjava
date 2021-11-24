package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Component
public class UserRoleExtractor implements ResultSetExtractor<Map<Integer, User>> {
    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    @Override
    public Map<Integer, User> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, User> data = new LinkedHashMap<>();
        while (rs.next()) {
            int id = rs.getInt("id");

            if (data.containsKey(id)) {
                Set<Role> roles = new HashSet<>(data.get(id).getRoles());
                roles.add(Role.valueOf(rs.getString("roles")));
                data.get(id).setRoles(roles);
                continue;
            }

            data.put(id, ROW_MAPPER.mapRow(rs, id));
        }
        return data;
    }
}