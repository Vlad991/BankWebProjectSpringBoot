package com.mybank.controller;

import com.mybank.dto.CreditCardDTO;
import com.mybank.dto.UserDTO;
import com.mybank.service.CreditCardControllerService;
import com.mybank.service.UserControllerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin
public class ClientController {
    private UserControllerService userControllerService;
    private CreditCardControllerService creditCardControllerService;

    public ClientController(UserControllerService userControllerService, CreditCardControllerService creditCardControllerService) {
        this.userControllerService = userControllerService;
        this.creditCardControllerService = creditCardControllerService;
    }

    @GetMapping(value = "/{login}/info")
    public UserDTO clientInfo(@PathVariable("login") String login) {
        return userControllerService.findUserByLogin(login);
    }

    @GetMapping(value = "/{login}/cards")
    public List<CreditCardDTO> clientCards(@PathVariable("login") String login) {
        List<CreditCardDTO> creditCardDTOList = creditCardControllerService.findUserCrediCardList(login);
        return creditCardDTOList;
    }

//    @GetMapping(value = "/{login}/settings")
//    public List<CreditCardDTO> clientCards(@PathVariable("login") String login) {
//        //todo
//    }
}
