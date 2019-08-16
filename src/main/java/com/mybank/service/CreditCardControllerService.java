package com.mybank.service;

import com.mybank.converter.CreditCardConverter;
import com.mybank.dto.CreditCardDTO;
import com.mybank.dto.UserDTO;
import com.mybank.entity.CreditCard;
import com.mybank.exception.UserLoginNotNullException;
import com.mybank.service.data.CreditCardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardControllerService {
    private CreditCardService creditCardService;
    private CreditCardConverter creditCardConverter;

    public List<CreditCardDTO> findUserCrediCardList(String login) {
        if (login == null) {
            throw new UserLoginNotNullException("User login is required");
        }
        List<CreditCard> creditCardList = creditCardService.findUserCreditCardList(login);
        List<CreditCardDTO> creditCardDTOList = creditCardConverter.convertToListDto(creditCardList);
        return creditCardDTOList;
    }
}
