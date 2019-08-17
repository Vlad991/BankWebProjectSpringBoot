package com.mybank.service;

import com.mybank.converter.CreditCardConverter;
import com.mybank.converter.UserConverter;
import com.mybank.dto.CreditCardDTO;
import com.mybank.dto.UserDTO;
import com.mybank.entity.CreditCard;
import com.mybank.entity.CreditCardStatus;
import com.mybank.entity.User;
import com.mybank.service.data.CreditCardRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardRegistrationControllerService {
    private CreditCardConverter creditCardConverter;
    @Autowired
    private UserConverter userConverter;
    private CreditCardRegistrationService creditCardRegistrationService;
    private ApplicationEventPublisher applicationEventPublisher;

    public CreditCardRegistrationControllerService(CreditCardConverter creditCardConverter,
                                                   CreditCardRegistrationService creditCardRegistrationService,
                                                   ApplicationEventPublisher applicationEventPublisher) {
        this.creditCardConverter = creditCardConverter;
        this.creditCardRegistrationService = creditCardRegistrationService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public CreditCardDTO registerNewCreditCard(CreditCardDTO newCreditCardDTO) {
        newCreditCardDTO.setStatus(CreditCardStatus.OPEN);
        CreditCard newCreditCard = creditCardConverter.convertToEntity(newCreditCardDTO);
        CreditCardDTO savedNewCreditCardDTO =
                creditCardConverter.convertToDto(creditCardRegistrationService.registerNewCreditCard(newCreditCard));
        savedNewCreditCardDTO.setPin(newCreditCardDTO.getPin());
        applicationEventPublisher.publishEvent(savedNewCreditCardDTO);
        return savedNewCreditCardDTO;
    }

    public CreditCardDTO getNewCreditCardForUser(UserDTO userDTO) {
        User user = userConverter.convertToEntity(userDTO);
        CreditCard newCreditCard = creditCardRegistrationService.getNewCreditCardForUser(user);
        CreditCardDTO newCreditCardDTO = creditCardConverter.convertToDto(newCreditCard);
        return newCreditCardDTO;
    }
}
