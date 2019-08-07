package com.mybank.service;

import com.mybank.converter.UserConverter;
import com.mybank.dto.UserDTO;
import com.mybank.entity.User;
import com.mybank.service.data.UserRegistrationService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationControllerService {
    private UserConverter userConverter;
    private UserRegistrationService userRegistrationService;
    private ApplicationEventPublisher applicationEventPublisher;

    public UserRegistrationControllerService(UserConverter userConverter,
                                             UserRegistrationService userRegistrationService,
                                             ApplicationEventPublisher applicationEventPublisher) {
        this.userConverter = userConverter;
        this.userRegistrationService = userRegistrationService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public UserDTO registerNewUser(UserDTO newUserDTO) {
        User newUser = userConverter.convertToEntity(newUserDTO);
        UserDTO savedNewUserDTO = userConverter.convertToDto(userRegistrationService.registerNewUser(newUser));
        savedNewUserDTO.setPassword(newUserDTO.getPassword());
        applicationEventPublisher.publishEvent(savedNewUserDTO);
        return savedNewUserDTO;
    }
}
