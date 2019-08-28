package com.mybank.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybank.dto.Payload;
import com.mybank.dto.SendSumDTO;
import com.mybank.service.CreditCardControllerService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
@EnableBinding(SendSumInput.class)
@Setter
@NoArgsConstructor
public class SendSumListener {
    private CreditCardControllerService creditCardControllerService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public SendSumListener(CreditCardControllerService creditCardControllerService) {
        this.creditCardControllerService = creditCardControllerService;
    }

    @StreamListener(target = SendSumInput.INPUT)
    public void onTenantEvent(Message<Payload> message) {
        Payload<LinkedHashMap> payload = message.getPayload();
        SendSumDTO sendSumDTO = objectMapper.convertValue(payload.getObjectToSend(), SendSumDTO.class);
        creditCardControllerService
                .sendSum(sendSumDTO.getSenderCardNumber(),
                        sendSumDTO.getReceiverCardNumber(),
                        sendSumDTO.getSum());
    }
}
