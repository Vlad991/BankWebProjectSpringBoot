package com.mybank.service;

import com.mybank.converter.CreditCardConverter;
import com.mybank.dto.CreditCardDTO;
import com.mybank.entity.CreditCard;
import com.mybank.entity.CreditCardStatus;
import com.mybank.exception.NotEnoughCreditCardSum;
import com.mybank.exception.UserLoginNotNullException;
import com.mybank.service.data.CreditCardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardControllerService {
    private CreditCardService creditCardService;
    private CreditCardConverter creditCardConverter;

    public CreditCardControllerService(CreditCardService creditCardService, CreditCardConverter creditCardConverter) {
        this.creditCardService = creditCardService;
        this.creditCardConverter = creditCardConverter;
    }

    public List<CreditCardDTO> findAll() {
        List<CreditCard> creditCardList = creditCardService.findAll();
        return creditCardConverter.convertToListDto(creditCardList);
    }

    public List<CreditCardDTO> findUserCreditCardList(String login) {
        if (login == null) {
            throw new UserLoginNotNullException("User login is required");
        }
        List<CreditCard> creditCardList = creditCardService.findUserCreditCardList(login);
        List<CreditCardDTO> creditCardDTOList = creditCardConverter.convertToListDto(creditCardList);
        return creditCardDTOList;
    }

    public CreditCardDTO setCreditCardStatus(String number, CreditCardStatus status) {
        CreditCard creditCard = creditCardService.setCreditCardStatus(number, status);
        CreditCardDTO creditCardDTO = creditCardConverter.convertToDto(creditCard);
        return creditCardDTO;
    }

    public int sendSum(String senderCardNumber, String receiverCardNumber, int sum) {
        CreditCardDTO senderCardDTO = findCreditCardByNumber(senderCardNumber);
        if (senderCardDTO.getSum() < sum) {
            throw new NotEnoughCreditCardSum("Sender credit card has not enough money");
        }
        return creditCardService.sendSum(senderCardNumber, receiverCardNumber, sum);
    }

    public CreditCardDTO findCreditCardByNumber(String number) {
        CreditCard creditCard = creditCardService.findCreditCardByNumber(number);
        CreditCardDTO creditCardDTO = creditCardConverter.convertToDto(creditCard);
        return creditCardDTO;
    }
}
