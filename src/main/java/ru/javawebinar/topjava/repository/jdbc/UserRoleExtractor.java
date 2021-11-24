package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class UserRoleExtractor implements ResultSetExtractor<Map<Integer, List<User>>> {
    @Override
    public Map<Integer, List<User>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, List<User>> data = new LinkedHashMap<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            data.putIfAbsent(id, new ArrayList<>());
            if (data.get(id).size() == 1) {
                Set<Role> roles = new HashSet<>(data.get(id).get(0).getRoles());
                roles.add(Role.valueOf(rs.getString("roles")));
                data.get(id).get(0).setRoles(roles);
                continue;
            }

            User user = new User();
            user.setId(id);
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setCaloriesPerDay(rs.getInt("calories_per_day"));
            user.setRoles(Set.of(Role.valueOf(rs.getString("roles"))));
            data.get(id).add(user);
        }
        return data;
    }
}