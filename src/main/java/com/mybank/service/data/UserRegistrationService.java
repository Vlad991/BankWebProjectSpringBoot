package com.mybank.service.data;

import com.mybank.entity.User;
import com.mybank.exception.UserAlreadyExistsException;
import com.mybank.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService {
    private UserRepository userRepository;

    public UserRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User registerNewUser(User newUser) {
        User oldUser = userRepository.findByLogin(newUser.getLogin());
        if (oldUser != null) {
            throw new UserAlreadyExistsException("User is already registered");
        }
        return userRepository.save(newUser);
    }
}
