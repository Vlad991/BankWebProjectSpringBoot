package com.mybank.service.data;

import com.mybank.entity.CreditCard;
import com.mybank.entity.CreditCardStatus;
import com.mybank.exception.CardNotFoundException;
import com.mybank.repository.CreditCardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreditCardService {
    //    @Autowired todo
    CreditCardRepository creditCardRepository;

    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public List<CreditCard> findAll() {
        return creditCardRepository.findAll();
    }

    @Transactional // todo right parameter
    public int sendSum(String senderCardNumber, String receiverCardNumber, int sum) {
        CreditCard senderCard = findCreditCardByNumber(senderCardNumber);
        CreditCard receiverCard = findCreditCardByNumber(receiverCardNumber);
        senderCard.setSum(senderCard.getSum() - sum);
        receiverCard.setSum(receiverCard.getSum() + sum);
        return sum;
    }

    public CreditCard findCreditCardByNumber(String number) {
        return creditCardRepository.findByNumber(number);
    }

    public List<CreditCard> findUserCreditCardList(String login) {
        return creditCardRepository.findCreditCardsByClient_Login(login);
    }

    @Transactional
    public CreditCard setCreditCardStatus(String number, CreditCardStatus status) {
        CreditCard creditCard = creditCardRepository.findByNumber(number);
        if (creditCard == null) {
            throw new CardNotFoundException();
        }
        creditCard.setStatus(status);
        return creditCardRepository.save(creditCard);  // todo save(ban)???
    }
}
