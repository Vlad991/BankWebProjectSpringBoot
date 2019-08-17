package com.mybank.controller;

import com.mybank.dto.CreditCardDTO;
import com.mybank.dto.UserDTO;
import com.mybank.service.CreditCardRegistrationControllerService;
import com.mybank.service.UserRegistrationControllerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping
public class UserRegistrationController {
    private UserRegistrationControllerService userRegistrationControllerService;
    private CreditCardRegistrationControllerService creditCardRegistrationControllerService;

    public UserRegistrationController(UserRegistrationControllerService userRegistrationControllerService) {
        this.userRegistrationControllerService = userRegistrationControllerService;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity registration(@Valid @RequestBody UserDTO userDTO) {
        CreditCardDTO newCreditCardDTO = creditCardRegistrationControllerService.getNewCreditCardForUser(userDTO);
        creditCardRegistrationControllerService.registerNewCreditCard(newCreditCardDTO);
        userRegistrationControllerService.registerNewUser(userDTO);
        //todo redirect to card registration
        return ResponseEntity.accepted().build();
    }
}
