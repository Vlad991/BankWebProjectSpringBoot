package com.mybank.service.data;

import com.mybank.entity.Address;
import com.mybank.entity.User;
import com.mybank.exception.UserAlreadyExistsException;
import com.mybank.repository.AddressRepository;
import com.mybank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService {
    private UserRepository userRepository;
    private AddressRepository addressRepository;

    public UserRegistrationService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public User registerNewUser(User newUser) {
        User existingUser = userRepository.findByLogin(newUser.getLogin());
        if (existingUser != null) {
            throw new UserAlreadyExistsException("User is already registered");
        }
        Address existingAddress = addressRepository.findByCountryAndCityAndStreetAndPostcode(
                newUser.getAddress().getCountry(),
                newUser.getAddress().getCity(),
                newUser.getAddress().getStreet(),
                newUser.getAddress().getPostcode()
        );
        if (existingAddress != null) {
            newUser.setAddress(existingAddress);
        } else {
            addressRepository.save(newUser.getAddress());
        }
        return userRepository.save(newUser);
    }
}
