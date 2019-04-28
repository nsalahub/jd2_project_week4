package com.gmail.salahub.nikolay.service.converters;

import com.gmail.salahub.nikolay.repository.model.User;
import com.gmail.salahub.nikolay.service.model.UserDTO;

public interface UserConverter {

    User fromDTO(UserDTO userDTO);

    UserDTO toDTO(User user);
}
