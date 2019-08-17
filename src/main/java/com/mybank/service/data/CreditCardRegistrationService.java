package com.mybank.service.data;

import com.mybank.entity.CreditCard;
import com.mybank.entity.CreditCardStatus;
import com.mybank.entity.User;
import com.mybank.exception.CreditCardAlreadyExistsException;
import com.mybank.repository.CreditCardRepository;
import com.mybank.service.data.generator.CreditCardGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreditCardRegistrationService {
    private CreditCardRepository creditCardRepository;
    @Autowired
    private CreditCardGenerator creditCardGenerator;

    public CreditCardRegistrationService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Transactional
    public CreditCard registerNewCreditCard(CreditCard newCreditCard) {
        CreditCard oldCrediCard = creditCardRepository.findByNumber(newCreditCard.getNumber());
        if (oldCrediCard != null) {
            throw new CreditCardAlreadyExistsException("Credit card is already registered");
        }
        return creditCardRepository.save(newCreditCard);
    }

    public CreditCard getNewCreditCardForUser(User user) {
        CreditCard newCreditCard = new CreditCard();
        newCreditCard.setNumber(creditCardGenerator.getNewCardNumber());
        newCreditCard.setClient(user);
        newCreditCard.setCode(creditCardGenerator.getCardCode());
        newCreditCard.setDate(creditCardGenerator.getCardDate());
        newCreditCard.setSum(0);
        newCreditCard.setStatus(CreditCardStatus.UNREGISTERED);
        return newCreditCard;
    }
}
