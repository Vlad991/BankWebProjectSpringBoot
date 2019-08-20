package com.mybank.controller;

import com.mybank.dto.CreditCardDTO;
import com.mybank.dto.UserDTO;
import com.mybank.service.CreditCardRegistrationControllerService;
import com.mybank.service.UserRegistrationControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping
public class UserRegistrationController {
    private UserRegistrationControllerService userRegistrationControllerService;
    private CreditCardRegistrationControllerService creditCardRegistrationControllerService;

    public UserRegistrationController(UserRegistrationControllerService userRegistrationControllerService, CreditCardRegistrationControllerService creditCardRegistrationControllerService) {
        this.userRegistrationControllerService = userRegistrationControllerService;
        this.creditCardRegistrationControllerService = creditCardRegistrationControllerService;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity registration(@Valid @RequestBody UserDTO userDTO) {
        userRegistrationControllerService.registerNewUser(userDTO);
        CreditCardDTO newCreditCardDTO = creditCardRegistrationControllerService.getNewCreditCardForUser(userDTO);
        creditCardRegistrationControllerService.registerNewCreditCard(newCreditCardDTO);
        //todo redirect to card registration
        return ResponseEntity.accepted().build();
    }
}
