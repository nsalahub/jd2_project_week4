package com.gmail.salahub.nikolay.service.impl;

import com.gmail.salahub.nikolay.repository.UserRepository;
import com.gmail.salahub.nikolay.repository.connection.ConnectionHandler;
import com.gmail.salahub.nikolay.repository.exception.UserRepositoryException;
import com.gmail.salahub.nikolay.repository.model.User;
import com.gmail.salahub.nikolay.service.UserService;
import com.gmail.salahub.nikolay.service.converters.UserConverter;
import com.gmail.salahub.nikolay.service.exception.UserServiceException;
import com.gmail.salahub.nikolay.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String USER_ADD_ERROR_SERVICE_MESSAGE = "Add user method error at service module, check console";
    private static final String USERS_GETTING_ERROR_MESSAGE = "Error with getting list of users  at service module";
    private static final String USER_GETTING_ERROR_MESSAGE = "Error with getting user by username  at service module";

    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final ConnectionHandler connectionHandle;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserConverter userConverter, ConnectionHandler connectionHandle) {
        this.connectionHandle = connectionHandle;
        this.userConverter = userConverter;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO add(UserDTO userDTO) {
        try (Connection connection = connectionHandle.getConnection()) {
            try {
                connection.setAutoCommit(false);
                User user = userConverter.fromDTO(userDTO);
                User savedUser = userRepository.add(user, connection);
                UserDTO savedDTO = userConverter.toDTO(savedUser);
                connection.commit();
                return savedDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserServiceException(USER_ADD_ERROR_SERVICE_MESSAGE, e);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserServiceException(USER_ADD_ERROR_SERVICE_MESSAGE, e);
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        List<UserDTO> savedUsers = new ArrayList<>();
        try (Connection connection = connectionHandle.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<User> users = userRepository.getUsers(connection);
                for (User user : users) {
                    savedUsers.add(userConverter.toDTO(user));
                }
                connection.commit();
                return savedUsers;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserServiceException(USERS_GETTING_ERROR_MESSAGE, e);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserServiceException(USERS_GETTING_ERROR_MESSAGE, e);
        }
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        try (Connection connection = connectionHandle.getConnection()) {
            try {
                connection.setAutoCommit(false);
                User user = userRepository.getUserByUsername(username, connection);
                UserDTO savedUser = userConverter.toDTO(user);
                connection.commit();
                return savedUser;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new UserRepositoryException(USERS_GETTING_ERROR_MESSAGE, e);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserRepositoryException(USERS_GETTING_ERROR_MESSAGE, e);
        }
    }
}
