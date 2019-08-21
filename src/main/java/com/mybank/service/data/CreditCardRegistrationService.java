package com.mybank.service.data;

import com.mybank.entity.CreditCard;
import com.mybank.entity.CreditCardStatus;
import com.mybank.entity.User;
import com.mybank.exception.CreditCardAlreadyExistsException;
import com.mybank.exception.UserNotFoundException;
import com.mybank.repository.CreditCardRepository;
import com.mybank.service.data.generator.CreditCardGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreditCardRegistrationService {
    private CreditCardRepository creditCardRepository;
    private CreditCardGenerator creditCardGenerator;
    private UserService userService;

    public CreditCardRegistrationService(CreditCardRepository creditCardRepository, CreditCardGenerator creditCardGenerator, UserService userService) {
        this.creditCardRepository = creditCardRepository;
        this.creditCardGenerator = creditCardGenerator;
        this.userService = userService;
    }

    @Transactional
    public CreditCard registerNewCreditCard(CreditCard newCreditCard) {
        CreditCard existingCreditCard = creditCardRepository.findByNumber(newCreditCard.getNumber());
        if (existingCreditCard != null) {
            throw new CreditCardAlreadyExistsException("Credit card is already registered");
        }
        User existingClient = userService.findUserByLogin(newCreditCard.getClient().getLogin());
        if (existingClient == null) {
            throw new UserNotFoundException();
        }
        newCreditCard.setClient(existingClient);
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
