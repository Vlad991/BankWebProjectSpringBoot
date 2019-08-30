package com.mybank.service.data;

import com.mybank.entity.PrivateMessage;
import com.mybank.entity.User;
import com.mybank.repository.PrivateMessageRepository;
import com.mybank.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrivateMessageService {
    private PrivateMessageRepository privateMessageRepository;
    private UserRepository userRepository;

    public PrivateMessageService(PrivateMessageRepository privateMessageRepository, UserRepository userRepository) {
        this.privateMessageRepository = privateMessageRepository;
        this.userRepository = userRepository;
    }

    @Transactional
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
        privateMessage.setMessage(privateMessageBody);
        privateMessageRepository.save(privateMessage);
    }

    public List<PrivateMessage> getAllReceivedPrivateMessages(String receiverLogin) {
        return privateMessageRepository.findAllByReceiver_Login(receiverLogin);
    }
}
