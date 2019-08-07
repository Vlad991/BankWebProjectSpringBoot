package com.mybank.service;

import com.mybank.converter.CommentConverter;
import com.mybank.converter.PrivateMessageConverter;
import com.mybank.dto.SendMessage;
import com.mybank.entity.Comment;
import com.mybank.entity.PrivateMessage;
import com.mybank.service.data.CommentService;
import com.mybank.service.data.PrivateMessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSocketService {
    private PrivateMessageService privateMessageService;
    private CommentService commentService;
    private PrivateMessageConverter privateMessageConverter;
    private CommentConverter commentConverter;

    public WebSocketService(PrivateMessageService privateMessageService, CommentService commentService, PrivateMessageConverter privateMessageConverter, CommentConverter commentConverter) {
        this.privateMessageService = privateMessageService;
        this.commentService = commentService;
        this.privateMessageConverter = privateMessageConverter;
        this.commentConverter = commentConverter;
    }

    public void savePrivateMessage(String sender, String receiver, String message) {
        privateMessageService.savePrivateMessageToDatabase(sender, receiver, message);
    }

    public void saveComment(String sender, String messageToSend) {
        commentService.saveComment(sender, messageToSend);
    }

    public List<SendMessage> getAllMessages(String login) {
        List<PrivateMessage> privateMessages = privateMessageService.getAllReceivedPrivateMessages(login);
        List<SendMessage> privateMessageDTOs = privateMessageConverter.toListPrivateMessageDTO(privateMessages);
        List<Comment> comments = commentService.getAllComments();
        List<SendMessage> commentDTOs = null;
        if (comments != null) {
            commentDTOs = commentConverter.toListCommentDTO(comments);
        }
        if (privateMessageDTOs != null && commentDTOs != null) {
            privateMessageDTOs.addAll(commentDTOs);
            return privateMessageDTOs;
        }
        if (privateMessageDTOs != null && commentDTOs == null) {
            return privateMessageDTOs;
        }
        if (privateMessageDTOs == null && commentDTOs != null) {
            return commentDTOs;
        }
        return null;
    }
}
