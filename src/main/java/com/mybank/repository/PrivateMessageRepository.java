package com.mybank.repository;

import com.mybank.entity.PrivateMessage;
import com.mybank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long> {
    List<PrivateMessage> findAllByReceiver_Login(String login);
}
