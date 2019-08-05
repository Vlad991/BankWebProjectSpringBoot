package com.mybank.converter;

import com.mybank.dto.MessageType;
import com.mybank.dto.SendMessage;
import com.mybank.entity.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageConverter {

    public List<SendMessage> toListMessageDTO(List<Message> privateMessages) {
        return privateMessages.stream()
                .map(entity -> fromMessageEntityToDto(entity))
                .collect(Collectors.toList());
    }

    public SendMessage fromMessageEntityToDto(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setType(MessageType.PRIVATE);
        sendMessage.setSender(message.getSender().getLogin());
        sendMessage.setMessage(message.getMessage());
        return sendMessage;
    }
}
