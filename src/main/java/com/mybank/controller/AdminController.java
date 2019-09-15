package com.mybank.controller;

import com.mybank.dto.CreditCardDTO;
import com.mybank.dto.UserDTO;
import com.mybank.entity.BlockingReason;
import com.mybank.entity.CreditCardStatus;
import com.mybank.service.CreditCardControllerService;
import com.mybank.service.UserControllerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    private UserControllerService userControllerService;
    private CreditCardControllerService creditCardControllerService;

    public AdminController(UserControllerService userControllerService, CreditCardControllerService creditCardControllerService) {
        this.userControllerService = userControllerService;
        this.creditCardControllerService = creditCardControllerService;
    }

    @PatchMapping(value = "/block/{login}")
    public UserDTO blockUser(@PathVariable("login") String login, @RequestBody String reason) {
        return userControllerService.blockUser(login, BlockingReason.valueOf(reason));
    }

    @DeleteMapping(value = "/block/{login}")
    public UserDTO unblockUser(@PathVariable("login") String login) {
        return userControllerService.unblockUser(login);
    }

    @GetMapping(value = "/clients")
    public List<UserDTO> findAllClients() {
        return userControllerService.findAll();
    }

    @GetMapping(value = "/cards")
    public List<CreditCardDTO> findAllCreditCards() {
        return creditCardControllerService.findAll();
    }

    @PutMapping(value = "/{number}/change")
    public ResponseEntity changeCreditCardStatus(@PathVariable("number") String number) {
        CreditCardDTO creditCardDTO = creditCardControllerService.findCreditCardByNumber(number);
        switch (creditCardDTO.getStatus()) {
            case OPEN: {
                creditCardControllerService.setCreditCardStatus(number, CreditCardStatus.BLOCKED);
                break;
            }
            case BLOCKED: {
                creditCardControllerService.setCreditCardStatus(number, CreditCardStatus.OPEN);
                break;
            }
        }
        return ResponseEntity.accepted().build();
    }
}
