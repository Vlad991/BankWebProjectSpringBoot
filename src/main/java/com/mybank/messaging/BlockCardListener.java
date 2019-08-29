package com.mybank.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybank.dto.Payload;
import com.mybank.dto.SendSumDTO;
import com.mybank.entity.CreditCardStatus;
import com.mybank.service.CreditCardControllerService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
@EnableBinding(BlockCardInput.class)
@Setter
@NoArgsConstructor
public class BlockCardListener {
    @Autowired
    private CreditCardControllerService creditCardControllerService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public BlockCardListener(CreditCardControllerService creditCardControllerService) {
        this.creditCardControllerService = creditCardControllerService;
    }

    @StreamListener(target = BlockCardInput.INPUT)
    public void onTenantEvent(Message<Payload> message) {
        Payload<LinkedHashMap> payload = message.getPayload();
        String number = objectMapper.convertValue(payload.getObjectToSend(), String.class);
        creditCardControllerService.setCreditCardStatus(number, CreditCardStatus.BLOCKED);
    }
}
