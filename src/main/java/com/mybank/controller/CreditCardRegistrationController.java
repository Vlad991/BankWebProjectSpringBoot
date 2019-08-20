package com.mybank.controller;

import com.mybank.dto.CreditCardDTO;
import com.mybank.dto.UserDTO;
import com.mybank.service.CreditCardRegistrationControllerService;
import com.mybank.service.UserControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping
public class CreditCardRegistrationController {
    private CreditCardRegistrationControllerService creditCardRegistrationControllerService;
    private UserControllerService userControllerService;

    public CreditCardRegistrationController(CreditCardRegistrationControllerService creditCardRegistrationControllerService, UserControllerService userControllerService) {
        this.creditCardRegistrationControllerService = creditCardRegistrationControllerService;
        this.userControllerService = userControllerService;
    }

    //    @PostMapping(value = "/card-registration/{login}")
//    public ResponseEntity registration(@PathVariable("login") String login) {
//        UserDTO userDTO = userControllerService.findUserByLogin(login);
//        UserDTO registeredUser = userControllerService.saveUser(userDTO);
//        CreditCardDTO newCreditCardDTO = creditCardRegistrationControllerService.getNewCreditCardForUser(registeredUser);
//        creditCardRegistrationControllerService.registerNewCreditCard(newCreditCardDTO);
//        return ResponseEntity.accepted().build();
//    }
}
