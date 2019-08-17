package com.mybank.controller.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybank.dto.MessageType;
import com.mybank.dto.ReceiveMessage;
import com.mybank.dto.SendMessage;
import com.mybank.entity.User;
import com.mybank.service.WebSocketService;
import com.mybank.service.data.ActiveClientsService;
import com.mybank.service.data.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
//@Component
public class ManagerWebSocketController extends TextWebSocketHandler {

    public final static String CLIENT_LOGIN = "client_login";

    @Autowired
    private UserService userService;

    @Autowired
    private ActiveClientsService activeClientsService;

    @Autowired
    private WebSocketService webSocketService;

    private Map<String, WebSocketSession> activeClients = new ConcurrentHashMap<>();

    private Map<String, WebSocketSession> activeManagers = new ConcurrentHashMap<>();

    @Autowired
    private Validator validator;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        try {
            String login = (String) session.getAttributes().get(CLIENT_LOGIN);
            User client = userService.findUserByLogin(login);
            if (client != null) {
                if (client.getBlocked() != null) {
                    sendErrorMessage(session, "You are blocked");
                    return;
                }

                activeClientsService.addActiveClient(client.getLogin(), session);
                // activeManagers.put(...)
                sendActiveClientList();
                sendActiveManagerList(); // todo ???
//                sendMessages(session); // todo

            } else {
                session.close();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            String login = (String) session.getAttributes().get("login");
            User user = userService.findUserByLogin(login);

            if (user != null && user.getBlocked() != null) {
                session.close();
            }

            String jsonString = message.getPayload();
            ReceiveMessage receiveMessage = mapper.readValue(jsonString, ReceiveMessage.class);

            Set<ConstraintViolation<ReceiveMessage>> errors = validator.validate(receiveMessage);
            if (!errors.isEmpty()) {
                sendErrorMessage(session, errors.iterator().next().getMessage());
                return;
            }

            switch (receiveMessage.getType()) {
                case PRIVATE: {
                    if (receiveMessage.getReceiver() == null) {
                        sendErrorMessage(session, "Receiver is required.");
                        return;
                    }
                    if (userService.findUserByLogin(receiveMessage.getReceiver()) == null) {
                        sendErrorMessage(session, "Receiver was not found.");
                        return;
                    }

                    if (receiveMessage.getMessage() == null) {
                        sendErrorMessage(session, "Message is required");
                        return;
                    }
                    WebSocketSession receiverSession = activeUsers.get(receiveMessage.getReceiver());
                    if (receiverSession == null) {
                        webSocketService.saveMessage(login,
                                receiveMessage.getReceiver(),
                                receiveMessage.getMessage());
                        return;
                    }

                    sendPrivateMessage(receiverSession, login, receiveMessage.getMessage());
                    break;
                }
                case COMMENT: {
                    if (receiveMessage.getMessage() == null) {
                        sendErrorMessage(session, "message is required");
                        return;
                    }
                    sendComment(login, receiveMessage.getMessage());
                    break;
                }
                case LOGOUT: {
                    activeUsers.remove(login);
                    sendActiveUsersList();
                    session.close();
                    break;
                }
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

    private void sendErrorMessage(WebSocketSession session, String message) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setMessage(message);
            sendMessage.setSender("system");
            sendMessage.setType(MessageType.PRIVATE);  // todo other message type (not private)
            TextMessage textMessage = new TextMessage(mapper.writeValueAsString(sendMessage));
            session.sendMessage(textMessage);
        } catch(IOException e){
            log.error(e.getMessage());
        }
    }

    private void sendActiveManagerList() {
        try {
            Set<String> activeManagerLogins = activeManagers.keySet();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setType(MessageType.ACTIVE_MANAGER_LIST);
            sendMessage.setSender("system");
            sendMessage.setMessage(null);
            sendMessage.setActiveUsers(activeManagerLogins);
            TextMessage textMessage = new TextMessage(mapper.writeValueAsString(sendMessage));
            sendAll(textMessage);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void sendAll(TextMessage textMessage) {
//        activeUsers.entrySet().stream()
//                .map(entry -> entry.getValue())
//                .forEach(session -> {
//                    try {
//                        session.sendMessage(textMessage);
//                    } catch (IOException e) {
//                        log.error(e.getMessage());
//                    }
//                }); todo
    }
}
