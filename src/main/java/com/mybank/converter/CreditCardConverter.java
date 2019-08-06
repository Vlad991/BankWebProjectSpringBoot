package com.mybank.converter;

import com.mybank.dto.CreditCardDTO;
import com.mybank.entity.CreditCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreditCardConverter {

    public CreditCardDTO convertToDto(CreditCard card) {
        CreditCardDTO cardDTO = new CreditCardDTO();
        cardDTO.setNumber(card.getNumber());
        cardDTO.setDate(card.getDate());
        cardDTO.setClient(card.getClient());
        cardDTO.setCode(card.getCode());
        cardDTO.setSum(card.getSum());
        cardDTO.setStatus(card.getStatus());
        return cardDTO;
    }

    public CreditCard convertToEntity(CreditCardDTO cardDTO) {
        CreditCard card = new CreditCard();
        card.setNumber(cardDTO.getNumber());
        card.setClient(cardDTO.getClient());
        card.setDate(cardDTO.getDate());
        card.setCode(cardDTO.getCode());
        card.setSum(cardDTO.getSum());
        card.setStatus(cardDTO.getStatus());
        return card;
    }

    public List<CreditCardDTO> convertToListDto(List<CreditCard> cards) {
        return cards.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
