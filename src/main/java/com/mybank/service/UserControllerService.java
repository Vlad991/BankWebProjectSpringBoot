package com.mybank.service;

import com.mybank.converter.UserConverter;
import com.mybank.dto.UserDTO;
import com.mybank.entity.BlockingReason;
import com.mybank.entity.User;
import com.mybank.exception.UserLoginNotNullException;
import com.mybank.service.data.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserControllerService {
    private UserService userService;
    private UserConverter userConverter;

    public UserControllerService(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    public List<UserDTO> findAll() {
        return userConverter.convertToListDto(userService.findAll());
    }

    public UserDTO findUserByLogin(String login) {
        if (login == null) {
            throw new UserLoginNotNullException("User login is required");
        }
        User user = userService.findUserByLogin(login);
        UserDTO userDTO = userConverter.convertToDto(user);
        return userDTO;
    }

    public UserDTO blockUser(String login, BlockingReason reason) {
        if (login == null) {
            throw new UserLoginNotNullException("User login is required");
        }
        return userConverter.convertToDto(userService.blockUser(login, reason)); // todo reason not null
    }

    public UserDTO unblockUser(String login) {
        if (login == null) {
            throw new UserLoginNotNullException("User login is required");
        }
        return userConverter.convertToDto(userService.unblockUser(login));
    }
}
