package com.gmail.salahub.nikolay.service.converters.impl;

import com.gmail.salahub.nikolay.repository.model.User;
import com.gmail.salahub.nikolay.service.UserService;
import com.gmail.salahub.nikolay.service.converters.UserConverter;
import com.gmail.salahub.nikolay.service.model.UserDTO;
import org.springframework.stereotype.Component;

@Component("userConverter")
public class UserConverterImpl implements UserConverter {

    @Override
    public User fromDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setUsername(userDTO.getUserName());
        return user;
    }

    @Override
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPassword(user.getPassword());
        userDTO.setUserName(user.getPassword());
        userDTO.setRole(user.getRole());
        return userDTO;
    }
}
