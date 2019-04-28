package com.gmail.salahub.nikolay.repository.impl;

import com.gmail.salahub.nikolay.repository.UserRepository;
import com.gmail.salahub.nikolay.repository.exception.UserRepositoryException;
import com.gmail.salahub.nikolay.repository.model.Item;
import com.gmail.salahub.nikolay.repository.model.Role;
import com.gmail.salahub.nikolay.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private static final String ERROR_INSERT_USER_REPOSITORY = "Insert user failed";
    private static final String ERROR_SELECT_LIST_USERS_REPOSITORY = "Select users failed";
    private static final String ERROR_SELECT_USER_REPOSITORY = "Select user failed";

    @Override
    public List<User> getUsers(Connection connection) {
        List<User> users = new ArrayList<>(Collections.emptyList());
        String sqlQuery = "SELECT * FROM users AS U LEFT JOIN role AS R ON U.role_id = R.id ";
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(getUser(resultSet));
                }
                return users;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UserRepositoryException(ERROR_SELECT_LIST_USERS_REPOSITORY, e);
        }
    }

    @Override
    public User add(User user, Connection connection) {
        String query = "INSERT INTO Users VALUES(NULL ,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().getName());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                User userWithID = getUserWithId(resultSet, user);
                return userWithID;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UserRepositoryException(ERROR_INSERT_USER_REPOSITORY, e);
        }
    }

    @Override
    public User getUserByUsername(String username, Connection connection) {
        String sqlQuery = "SELECT * FROM users AS U LEFT JOIN role AS R ON U.role_id = R.id  WHERE username =?";
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getUser(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UserRepositoryException(ERROR_SELECT_USER_REPOSITORY, e);
        }
        return null;
    }

    private User getUserWithId(ResultSet resultSet, User user) throws SQLException {
        User savedUser = new User();
        savedUser.setId(resultSet.getLong(1));
        savedUser.setPassword(user.getPassword());
        savedUser.setRole(user.getRole());
        savedUser.setUsername(user.getUsername());
        return savedUser;
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        Role role = new Role();
        role.setId(resultSet.getLong("id"));
        role.setName(resultSet.getString("name"));
        user.setRole(role);
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setId(resultSet.getLong("id"));
        return user;
    }
}
