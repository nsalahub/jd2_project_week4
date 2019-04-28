package com.gmail.salahub.nikolay.service;

import com.gmail.salahub.nikolay.repository.model.User;
import com.gmail.salahub.nikolay.service.model.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO add(UserDTO user);

    List<UserDTO> getUsers();

    UserDTO getUserByUsername(String username);
}
