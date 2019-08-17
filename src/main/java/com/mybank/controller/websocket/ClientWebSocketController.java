package com.mybank.controller.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybank.dto.MessageType;
import com.mybank.dto.ReceiveMessage;
import com.mybank.dto.SendMessage;
import com.mybank.dto.UserDTO;
import com.mybank.service.UserControllerService;
import com.mybank.service.WebSocketService;
import com.mybank.service.data.ActiveAdminsService;
import com.mybank.service.data.ActiveClientsService;
import com.mybank.service.data.ActiveManagersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Slf4j
//@Component
public class ClientWebSocketController extends TextWebSocketHandler {

    public final static String CLIENT_LOGIN = "client_login";

    @Autowired
    private UserControllerService userControllerService;

    @Autowired
    private ActiveClientsService activeClientsService;

    @Autowired
    private ActiveManagersService activeManagersService;

    @Autowired
    private ActiveAdminsService activeAdminsService;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private Validator validator;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        try {
            String login = (String) session.getAttributes().get(CLIENT_LOGIN);
            UserDTO client = userControllerService.findUserByLogin(login);
            if (client != null) {
                if (client.isBlocked()) {
                    sendErrorMessage(session, "You are blocked");
                    return;
                }

                activeClientsService.addActiveClient(client.getLogin(), session);
//                sendActiveClientList();
//                sendActiveManagerList(); // todo
                sendPrivateMessages(session);
                sendComments(session);
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
            String login = (String) session.getAttributes().get(CLIENT_LOGIN);
            UserDTO user = userControllerService.findUserByLogin(login);

            if (user != null && user.isBlocked()) {
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
                    if (userControllerService.findUserByLogin(receiveMessage.getReceiver()) == null) {
                        sendErrorMessage(session, "Receiver was not found.");
                        return;
                    }

                    if (receiveMessage.getMessage() == null) {
                        sendErrorMessage(session, "Message is required.");
                        return;
                    }
                    WebSocketSession receiverSession =
                            activeClientsService.getActiveClient(receiveMessage.getReceiver());
                    if (receiverSession == null) {
                        webSocketService.savePrivateMessage(login,
                                receiveMessage.getReceiver(),
                                receiveMessage.getMessage());
                        return;
                    }

                    sendPrivateMessage(receiverSession, login, receiveMessage.getMessage());
                    break;
                }
                case COMMENT: {
                    if (receiveMessage.getMessage() == null) {
                        sendErrorMessage(session, "Comment is required");
                        return;
                    }
                    sendComment(login, receiveMessage.getMessage());
                    break;
                }
                case LOGOUT: {
                    activeClientsService.removeActiveClient(login);
                    sendActiveClientList();
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
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void sendActiveClientList() {
        try {
            Set<String> activeClientLogins = activeClientsService.getActiveClientLogins();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setType(MessageType.ACTIVE_CLIENT_LIST);
            sendMessage.setSender("system");
            sendMessage.setMessage(null);
            sendMessage.setActiveUsers(activeClientLogins);
            TextMessage textMessage = new TextMessage(mapper.writeValueAsString(sendMessage));
            sendAllManagers(textMessage);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void sendAllManagers(TextMessage textMessage) { // to send all managers active clients list
        List<WebSocketSession> activeManagers = activeManagersService.getActiveManagerSessions();
        for (WebSocketSession activeManagerSession : activeManagers) {
            try {
                activeManagerSession.sendMessage(textMessage);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    private void sendAllUsers(TextMessage textMessage) { // to send all managers active clients
        List<WebSocketSession> activeManagers = activeManagersService.getActiveManagerSessions();
        List<WebSocketSession> activeClients = activeClientsService.getActiveClientSessions();
        List<WebSocketSession> activeAdmins = activeAdminsService.getActiveAdminSessions();
        for (WebSocketSession activeManagerSession : activeManagers) {
            try {
                activeManagerSession.sendMessage(textMessage);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        for (WebSocketSession activeClientSession : activeClients) {
            try {
                activeClientSession.sendMessage(textMessage);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        for (WebSocketSession activeAdminSession : activeAdmins) {
            try {
                activeAdminSession.sendMessage(textMessage);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    private void sendPrivateMessage(WebSocketSession receiveSession, String sender, String messageToSend) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setType(MessageType.PRIVATE);
            sendMessage.setSender(sender);
            sendMessage.setMessage(messageToSend);
            TextMessage textMessage = new TextMessage(mapper.writeValueAsString(sendMessage));
            receiveSession.sendMessage(textMessage);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void sendComment(String sender, String messageToSend) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setType(MessageType.COMMENT);
            sendMessage.setSender(sender);
            sendMessage.setMessage(messageToSend);
            webSocketService.saveComment(sender, messageToSend);
            TextMessage textMessage = new TextMessage(mapper.writeValueAsString(sendMessage));
            sendAllUsers(textMessage);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void sendPrivateMessages(WebSocketSession session) { // send all private messages when connection is established
        try {
            List<SendMessage> messages = webSocketService.getAllPrivateMessages((String) session.getAttributes().get(CLIENT_LOGIN));
            for (SendMessage sendMessage : messages) {
                session.sendMessage(new TextMessage(mapper.writeValueAsString(sendMessage)));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void sendComments(WebSocketSession session) { // send all comments when connection is established
        try {
            List<SendMessage> messages = webSocketService.getAllComments();
            for (SendMessage sendMessage : messages) {
                session.sendMessage(new TextMessage(mapper.writeValueAsString(sendMessage)));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
