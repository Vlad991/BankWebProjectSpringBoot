package com.mybank.controller;

import com.mybank.dto.CreditCardDTO;
import com.mybank.dto.UserDTO;
import com.mybank.service.CreditCardRegistrationControllerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping
public class CreditCardRegistrationController {
    private CreditCardRegistrationControllerService creditCardRegistrationControllerService;

    public CreditCardRegistrationController(CreditCardRegistrationControllerService creditCardRegistrationControllerService) {
        this.creditCardRegistrationControllerService = creditCardRegistrationControllerService;
    }

    @PostMapping(value = "/card-registration")
    public ResponseEntity registration(@Valid @RequestBody UserDTO userDTO) {
        CreditCardDTO newCreditCardDTO = creditCardRegistrationControllerService.getNewCreditCardForUser(userDTO);
        creditCardRegistrationControllerService.registerNewCreditCard(newCreditCardDTO);
        return ResponseEntity.accepted().build();
    }
}
