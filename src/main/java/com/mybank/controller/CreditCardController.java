package com.mybank.controller;

import com.mybank.dto.CreditCardDTO;
import com.mybank.entity.CreditCardStatus;
import com.mybank.service.CreditCardControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/card")
@CrossOrigin
public class CreditCardController {
    private CreditCardControllerService creditCardControllerService;

    public CreditCardController(CreditCardControllerService creditCardControllerService) {
        this.creditCardControllerService = creditCardControllerService;
    }

    @GetMapping(value = "/{number}/info")
    public CreditCardDTO cardInfo(@PathVariable("number") String number) {
        return creditCardControllerService.findCreditCardByNumber(number);
    }

    @PutMapping(value = "/{number}/send")
    public ResponseEntity sendSumToCreditCard(@PathVariable("number") String senderCardNumber,
                                              @RequestBody String receiverCardNumber,
                                              @RequestBody int sum) {
        int sendedSum = creditCardControllerService.sendSum(senderCardNumber, receiverCardNumber, sum);
        return ResponseEntity.accepted().build();
    }

    @PutMapping(value = "/{number}/block")
    public CreditCardDTO blockCreditCard(@PathVariable("number") String number) {
        return creditCardControllerService.setCreditCardStatus(number, CreditCardStatus.BLOCKED);
    }

//    @GetMapping(value = "/{number}/settings")
//    public List<CreditCardDTO> clientCards(@PathVariable("login") String login) {
//        //todo settings
//    }
}
