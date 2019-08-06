package com.mybank.service.data;

import com.mybank.entity.PrivateMessage;
import com.mybank.entity.User;
import com.mybank.repository.PrivateMessageRepository;
import com.mybank.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivateMessageService {

    private PrivateMessageRepository privateMessageRepository;

    private UserRepository userRepository;

    public PrivateMessageService(PrivateMessageRepository privateMessageRepository, UserRepository userRepository) {
        this.privateMessageRepository = privateMessageRepository;
        this.userRepository = userRepository;
    }

    public void savePrivateMessageToDatabase(String senderLogin, String receiverLogin, String privateMessageBody) {
        User receiver = userRepository.findByLogin(receiverLogin);
        User sender = userRepository.findByLogin(senderLogin);
        PrivateMessage privateMessage = new PrivateMessage();
        if (sender != null) {
            privateMessage.setSender(sender);
        }
        if (receiver != null) {
            privateMessage.setReceiver(receiver);
        }
        privateMessageRepository.save(privateMessage);
    }

    public List<PrivateMessage> getAllReceivedPrivateMessages(String receiverLogin) {
        return privateMessageRepository.findByReceiver_Login(receiverLogin);
    }
}
