package com.mybank.service.data;

import com.mybank.entity.Message;
import com.mybank.entity.User;
import com.mybank.repository.MessageRepository;
import com.mybank.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    private UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public void saveMessageToDatabase(String senderLogin, String receiverLogin, String messageBody) {
        User receiver = userRepository.findByLogin(receiverLogin);
        User sender = userRepository.findByLogin(senderLogin);
        Message message = new Message();
        if (sender != null) {
            message.setSender(sender);
        }
        if (receiver != null) {
            message.setReceiver(receiver);
        }
        messageRepository.save(message);
    }

    public List<Message> getAllReceivedMessages(String receiverLogin) {
        return messageRepository.findByReceiver_Login(receiverLogin);
    }
}
