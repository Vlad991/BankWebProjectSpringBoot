package com.mybank.service.data.generator;

import com.mybank.entity.carddate.CreditCardDate;
import com.mybank.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CreditCardGenerator {
    @Autowired
    private CreditCardRepository creditCardRepository;

    public String getNewCardNumber() {
        Long newNumber = 1414000000000000l;
        List<Long> numberList = new ArrayList<>();
        List<String> numbers = creditCardRepository.getAllNumbers();
        for (String number : numbers) {
            numberList.add(Long.parseLong(number));
        }
        for (int i = 1; i <= numberList.size() + 2; i++) {
            if (numberList.indexOf(newNumber + i) == -1) {
                newNumber = newNumber + i;
                break;
            }
        }
        return newNumber.toString();
    }

    public int getCardCode() {
        return (int) (100 + Math.random() * 899);
    }

    public CreditCardDate getCardDate() {
        Date currentDate = new Date();
        CreditCardDate creditCardDate = new CreditCardDate();
        creditCardDate.setMonth(currentDate.getMonth() + 1);
        creditCardDate.setYear(currentDate.getYear() - 100 + 20); // Credit Card create fot 20 years usage
        return creditCardDate;
    }
}
