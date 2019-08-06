package com.mybank.converter;

import com.mybank.dto.MessageType;
import com.mybank.dto.SendMessage;
import com.mybank.entity.PrivateMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PrivateMessageConverter {

    public List<SendMessage> toListPrivateMessageDTO(List<PrivateMessage> privateMessages) {
        return privateMessages.stream()
                .map(entity -> fromPrivateMessageEntityToDto(entity))
                .collect(Collectors.toList());
    }

    public SendMessage fromPrivateMessageEntityToDto(PrivateMessage message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setType(MessageType.PRIVATE);
        sendMessage.setSender(message.getSender().getLogin());
        sendMessage.setMessage(message.getMessage());
        return sendMessage;
    }
}
