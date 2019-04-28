package com.gmail.salahub.nikolay.repository;

import com.gmail.salahub.nikolay.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository {
    List<User> getUsers(Connection connection);

    User add(User user, Connection connection);

    User getUserByUsername(String username, Connection connection);
}
